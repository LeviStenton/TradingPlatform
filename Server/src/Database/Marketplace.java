package Database;

import java.util.List;

/**
 * Marketplace gets the current orders that have not been completed and attempts to pair them up
 */
public class Marketplace {

        public Marketplace(){
        }

        /**
         *
         */
        public void GroupAssets(){
                DBSource source = new DBSource();
                List<Integer> assetCount = source.GetAssetCount();
                List<Order> buyOrders;
                List<Order> sellOrders;
                //TODO you just changed assetCount to a list
                for(int i = 1; i < assetCount.size(); i++){
                        buyOrders = source.GetOrders(assetCount.get(i - 1),"B");
                        sellOrders = source.GetOrders(assetCount.get(i - 1),"S");
                        if(buyOrders.size() > 0 && sellOrders.size() > 0){
                               LoopBuyOrders(buyOrders,sellOrders,source);
                        }
                }
        }

        /**
         *
         */
        private void LoopBuyOrders(List<Order> buyOrders, List<Order> sellOrders, DBSource source){
                for(int buyI = 0; buyI < buyOrders.size(); buyI++)  {
                        LoopSellOrders(buyOrders,sellOrders,source , buyI);
                }
        }

        /**
         *
         */
        private void LoopSellOrders(List<Order> buyOrders, List<Order> sellOrders, DBSource source, int buyI){
                double buyOrderPrice;
                double sellOrderPrice;
                double buyOrderQuantity;
                double sellOrderQuantity;
                int buyOrderOrderID;
                int sellOrderOrderID;
                int buyOrderAssetID;
                double sellPriceTotal;

                for (Order sellOrder : sellOrders) {
                        buyOrderPrice = buyOrders.get(buyI).getPrice();
                        sellOrderPrice = sellOrder.getPrice();
                        buyOrderQuantity = buyOrders.get(buyI).getQuantity();
                        sellOrderQuantity = sellOrder.getQuantity();
                        buyOrderOrderID = buyOrders.get(buyI).getOrderID();
                        sellOrderOrderID = sellOrder.getOrderID();
                        buyOrderAssetID = buyOrders.get(buyI).getAssetID();

                        if (buyOrderPrice >= sellOrderPrice) {
                                buyOrders.get(buyI).setPrice(sellOrderPrice);
                                double remainingBuyQuantity = 0;
                                double remainingSellQuantity = 0;
                                if (buyOrderQuantity > sellOrderQuantity) {
                                        remainingBuyQuantity = buyOrderQuantity - sellOrderQuantity;
                                        buyOrderQuantity = sellOrderQuantity;
                                }
                                if (sellOrderQuantity > buyOrderQuantity) {
                                        remainingSellQuantity = sellOrderQuantity - buyOrderQuantity;
                                        sellOrderQuantity = buyOrderQuantity;
                                }

                                sellPriceTotal = buyOrderQuantity * sellOrderPrice;

                                //Adds credits to seller
                                source.ChangeOrgCredits(sellPriceTotal, source.OrderJoinOrgID(sellOrderOrderID), "+");
                                System.out.println("added " + sellPriceTotal + " credits to org " + source.OrderJoinOrgID(sellOrderOrderID));
                                //Adds asset to buyer
                                source.InsertOrgAsset(source.OrderJoinOrgID(buyOrderOrderID), buyOrderAssetID, buyOrderQuantity, "+");
                                System.out.println("added " + buyOrderQuantity + " , " + buyOrderAssetID + " to org " + source.OrderJoinOrgID(buyOrderOrderID));

                                buyOrders.get(buyI).setQuantity(buyOrderQuantity);
                                sellOrder.setQuantity(sellOrderQuantity);

                                //Remove buy/sell orders from orders DB
                                double tempBuyQuantity = buyOrderQuantity - sellOrderQuantity;
                                double tempSelluantity = sellOrderQuantity - buyOrderQuantity;

                                buyOrders.get(buyI).setCompleted("Y");
                                source.AddToOrderHistory(buyOrders.get(buyI));
                                if (tempBuyQuantity == 0 && remainingBuyQuantity == 0) {
                                        source.DeleteOrder(buyOrderOrderID);
                                } else {
                                        source.ChangeOrderQuantity(buyOrderOrderID, buyOrderQuantity, "-");
                                }

                                sellOrder.setCompleted("Y");
                                source.AddToOrderHistory(sellOrder);
                                if (tempSelluantity == 0 && remainingSellQuantity == 0) {
                                        source.DeleteOrder(sellOrderOrderID);
                                } else {
                                        source.ChangeOrderQuantity(sellOrderOrderID, sellOrderQuantity, "-");

                                }
                                break;
                        }
                }
        }
}
