import java.util.List;

public class Marketplace {
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

        private void LoopBuyOrders(List<Order> buyOrders, List<Order> sellOrders, DBSource source){
                boolean endLoop = false;
                for(int buyI = 0; buyI < buyOrders.size(); buyI++)  {
                        LoopSellOrders(buyOrders,sellOrders,source , buyI);
                }
        }

        private void LoopSellOrders(List<Order> buyOrders, List<Order> sellOrders, DBSource source, int buyI){
                double buyOrderPrice;
                double sellOrderPrice;
                double buyOrderQuantity;
                double sellOrderQuantity;
                int buyOrderOrderID;
                int sellOrderOrderID;
                int buyOrderAssetID;
                double sellPriceTotal;

                for(int q = 0; q < sellOrders.size(); q++) {
                        buyOrderPrice = buyOrders.get(buyI).getPrice();
                        sellOrderPrice = sellOrders.get(q).getPrice();
                        buyOrderQuantity = buyOrders.get(buyI).getQuantity();
                        sellOrderQuantity = sellOrders.get(q).getQuantity();
                        buyOrderOrderID = buyOrders.get(buyI).getOrderID();
                        sellOrderOrderID = sellOrders.get(q).getOrderID();
                        buyOrderAssetID = buyOrders.get(buyI).getAssetID();


                        if(buyOrderPrice >= sellOrderPrice){
                                buyOrders.get(buyI).setPrice(sellOrderPrice);
                                buyOrderPrice = buyOrders.get(buyI).getPrice();
                                double remainingBuyQuantity = 0;
                                double remainingSellQuantity = 0;
                                if(buyOrderQuantity > sellOrderQuantity){
                                        remainingBuyQuantity = buyOrderQuantity - sellOrderQuantity;
                                        buyOrderQuantity = sellOrderQuantity;
                                }
                                if(sellOrderQuantity > buyOrderQuantity){
                                        remainingSellQuantity = sellOrderQuantity - buyOrderQuantity;
                                        sellOrderQuantity = buyOrderQuantity;
                                }

                                sellPriceTotal = buyOrderQuantity * sellOrderPrice;

                                //Adds credits to seller
                                source.ChangeOrgCredits(sellPriceTotal, source.OrderJoinOrgID(sellOrderOrderID), "+");
                                System.out.println("added " + sellPriceTotal + " credits to org " + source.OrderJoinOrgID(sellOrderOrderID));
                                //Adds asset to buyer
                                source.InsertOrgAsset(source.OrderJoinOrgID(buyOrderOrderID),buyOrderAssetID,buyOrderQuantity, "+");
                                System.out.println("added " + buyOrderQuantity + " , " + buyOrderAssetID + " to org " + source.OrderJoinOrgID(buyOrderOrderID));



                                buyOrders.get(buyI).setQuantity(buyOrderQuantity);
                                sellOrders.get(q).setQuantity(sellOrderQuantity);

                                //Remove buy/sell orders from orders DB
                                double tempBuyQuantity = buyOrderQuantity - sellOrderQuantity;
                                double tempSelluantity = sellOrderQuantity - buyOrderQuantity;

                                if(tempBuyQuantity == 0 && remainingBuyQuantity == 0){
                                        buyOrders.get(buyI).setCompleted("Y");
                                        source.AddToOrderHistory(buyOrders.get(buyI));
                                        source.DeleteOrder(buyOrderOrderID);
                                }
                                else {
                                        buyOrders.get(buyI).setCompleted("Y");
                                        source.AddToOrderHistory(buyOrders.get(buyI));
                                        source.ChangeOrderQuantity(buyOrderOrderID,buyOrderQuantity,"-");
                                }

                                if(tempSelluantity == 0 && remainingSellQuantity == 0){
                                        sellOrders.get(q).setCompleted("Y");
                                        source.AddToOrderHistory(sellOrders.get(q));
                                        source.DeleteOrder(sellOrderOrderID);
                                }
                                else{
                                        sellOrders.get(q).setCompleted("Y");
                                        source.AddToOrderHistory(sellOrders.get(q));
                                        source.ChangeOrderQuantity(sellOrderOrderID,sellOrderQuantity,"-");

                                }




                                break;


                        }

                }
        }
}
