import java.util.List;

public class Marketplace {
        public void GroupAssets(){
                DBSource source = new DBSource();

                int assetCount = source.GetAssetCount();
                List<Order> buyOrders;
                List<Order> sellOrders;


                for(int i = 1; i < assetCount; i++){
                        buyOrders = source.GetOrders(i,"B");
                        sellOrders = source.GetOrders(i,"S");
                        if(buyOrders.size() > 0 && sellOrders.size() > 0){
                                for(int z = 0; z < buyOrders.size(); z++)  {
                                        for(int q = 0; q < sellOrders.size(); q++) {
                                                if(buyOrders.get(z).getPrice() >= sellOrders.get(q).getPrice()){
                                                        buyOrders.get(z).setPrice(sellOrders.get(q).getPrice());
                                                        if(buyOrders.get(z).getQuantity() == sellOrders.get(q).getQuantity()){
                                                                //sets orders to complete
                                                                buyOrders.get(z).setCompleted("Y");
                                                                sellOrders.get(q).setCompleted("Y");

                                                                //Adds credits to seller
                                                                source.ChangeOrgCredits(sellOrders.get(q).getPrice(), source.OrderJoinOrgID(sellOrders.get(q).getOrderID()), "+");
                                                                //Adds asset to buyer

                                                                source.AddToOrderHistory(buyOrders.get(z));
                                                                source.AddToOrderHistory(sellOrders.get(q));

                                                                //Orders finished
                                                                //buyOrders.remove(z);
                                                                //sellOrders.remove(q);
                                                                //break * 2
                                                                System.out.println("gg");
                                                        }
                                                        else if(buyOrders.get(z).getQuantity() > sellOrders.get(q).getQuantity()){
                                                                //Sell order is finished
                                                                //buyOrder quantity - sellOrder quantity
                                                        }
                                                        else if(buyOrders.get(z).getQuantity() < sellOrders.get(q).getQuantity()){
                                                                //buyOrder is finished
                                                                //sellOrder quantity - buyOrder quantity
                                                        }
                                                        //break;
                                                }

                                        }
                                }
                        }
                }

                //for(int i, to maxAssets)
                        //SELECT Asset_ID FROM Orders
                        //ORDER BY Asset_ID,date
                        //WHERE Asset_ID = i && Order type = B
                        //Store in buyList

                        //SELECT Asset_ID FROM Orders
                        //ORDER BY Asset_ID,date
                        //WHERE Asset_ID = i && Order type = S
                        //Store in sellList

                        //if(buyList.count >= 1 && sellList.count >= 1)
                                //for(int z; z <= buyList.Count; z++)
                                        //for(int q; q <= sellList.Count; q++)
                                                //if(buyList[z].price >= sellList[q].price){
                                                // buy
                                                //}









                //SELECT DISTINCT Asset_ID FROM Orders
                //ORDER BY Asset_ID;
                //Where order type = B
                //Store in array1

                //SELECT DISTINCT Asset_ID FROM Orders
                //ORDER BY Asset_ID;
                //Where order type = S
                //store in array2

                //Check array1 has every element of array2
                //if they match add to array3
                //Array 3 will be
        }
}
