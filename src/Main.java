import java.util.*;

public class Main {

    enum Search {
        UNIFORM_COST,
        A_STAR
    }

    public static void main(String[] args) {

//        System.out.println(Arrays.toString(args));

        // output names
//        "outputUniCost.txt";
//        "outputAstar.txt";
        // inital state: 87654321_
        // goal stat:    12345678_

//        Scanner userInput = new Scanner(System.in);
//        System.out.println("Please enter path to input file: ");
//        String line = userInput.nextLine();
//        System.out.println("you entered " + line);


        char[][] startState = {
                {'8', '7', '6'},
                {'5', '4', '3'},
                {'2', '1', ' '}
        };
        char[][] goalState = {
                {'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', ' '}
        };
        char[][] goalState2 = {
                {' ', '7', '6'},
                {'5', '4', '3'},
                {'2', '1', '8'}
        };
        char[][] goalState3 = {
                {'8', '7', ' '},
                {'5', '4', '6'},
                {'2', '1', '3'}
        };
//        System.out.println(startState == goalState);


        // TODO: move to uniformCostSearch :) ..... ;(

        Node startNode = new Node(startState);
        Node goalNode = new Node(goalState);


//        TreeSet<Node> unexpandedNodes = new TreeSet<>(); // TreeSet wil sort Nodes based on compareTo() in Node
//        TreeSet<Node> expandedNodes = new TreeSet<>();
//        TreeMap<State, > unexpandedNodes = new TreeSet<>(); // TreeSet wil sort Nodes based on compareTo() in Node
//        TreeSet<Node> expandedNodes = new TreeSet<>();
        ArrayList<Node> unexpandedNodes = new ArrayList<>();
        ArrayList<Node> expandedNodes = new ArrayList<>();


        /* !IMPORTANT
        Source; modified parts of 'solve()' method from provided code in Assignment1 CE213 2020
        */
        unexpandedNodes.add(startNode);
        while (unexpandedNodes.size() > 0){
//            Node node = unexpandedNodes.first(); // since it's sorted, first item will be lowest cost from root
//            expandedNodes.add(node);
//            unexpandedNodes.re
            // TODO: Sort by cost OR add to right position in list: add(i, element)
            Node node = unexpandedNodes.get(0); // since it is sorted the first item will be lowest cost from root
            unexpandedNodes.remove(0);
            expandedNodes.add(node);
            if(Arrays.deepEquals(node.getState(), goalState3)){
                System.out.println("winner winner chicken dinner!");
                return;
            }
            node.expand();
            boolean previouslyExplored;
            boolean hasBeenAdded;
            for (Node a : node.getPossiblePaths()) {
                previouslyExplored = false;

                for (Node b : unexpandedNodes) {
                    if(Arrays.deepEquals(a.getState(), b.getState())){
                        previouslyExplored = true;
                        if(a.getCost() < b.getCost()){
    //                        b.setCost(a.getCost());
    //                        b.setParent(a.getParent());
                            unexpandedNodes.set(unexpandedNodes.indexOf(b), a);
                        }
                    }
                }
                for (Node b : expandedNodes) {
                    if(Arrays.deepEquals(a.getState(), b.getState())){
                        previouslyExplored = true;
                        if(a.getCost() < b.getCost()){
    //                       pseudo: expandedNodes.put(b.cost = a.cost, b.parent = a.parent);
                            b.setCost(a.getCost());
                            b.setParent(a.getParent());
                            expandedNodes.set(expandedNodes.indexOf(b), b);
                        }
                    }
                }
                if(!previouslyExplored){
//                    hasBeenAdded = false;

//                    for (int i = 0; i < unexpandedNodes.size(); i++) {
//                        if(a.getCost() < unexpandedNodes.get(i).getCost()){
//                            // add the new node to the arraylist with position relative to cost of 'a'
//                            // so that unexpandedNodes is sorted by cost ascending.
//                            unexpandedNodes.add(i, a);
//                            hasBeenAdded = true;
//                            break;
//                        }
//                    }
//                    if(!hasBeenAdded)
                        unexpandedNodes.add(a);


                }
            }
        }


//        System.out.println("Start state: " + startNode + "Goal state: " + goalNode);

//        startNode.possibleNodes();
//        System.out.println(startNode.getPossiblePaths());

//        Search searchStrategy = queryUser();
//        System.out.println(searchStrategy);

//        HashSet<Node> queue = new HashSet<>();
//        ArrayList<Node> queue = new ArrayList<>();
//        TreeSet<Node> queue = new TreeSet<>();
//        TreeMap<Node, Integer> queue = new TreeMap<>();
//        ArrayList<Node> ExpandedNodes = new ArrayList<>();
//        ArrayList<Node> UnexpandedNodes = new ArrayList<>();

//        queue.add(startNode);
//        queue.add(goalNode);
//        queue.add(new Node(startState, startNode, 1));
//        queue.add(new Node(goalState2, startNode, 1));
//        queue.put(startNode, startNode.getCost());
//        queue.put(goalNode, goalNode.getCost());
//        System.out.println(queue);





//        Iterator<Node> i, j;
//        Node node = startNode;
//        int endNodeExpanded = 0;
//        int prevCost = 0;

//        while(endNodeExpanded < 200000){// && queue.size() > 0){
//
//
//
//            endNodeExpanded++;
//        }



        /*
        while(endNodeExpanded < 400000){
//            System.out.println(node);

            if(node.equals(goalNode)){
                System.out.println("node = goal state");
                break;
            }

            if(node.getCost() > prevCost){
                prevCost = node.getCost();
                System.out.println(prevCost);
            }


            node.expand();
//            System.out.println(node.getPossiblePaths());

            // temporarily add all options to queue
            queue.addAll(node.getPossiblePaths());

            ArrayList<Node> toRemoveFromQueue = new ArrayList<>();
            i = node.getPossiblePaths().iterator();
            while(i.hasNext()){
                Node iNode = i.next();

                j = queue.iterator();
                boolean onlyOne = false;
                while(j.hasNext()){
                    Node jNode = j.next();

                    if(iNode.equals(jNode)) { // if state is equal

                        // if we found a better route to a node -> update queue
                        if (iNode.getCost() < jNode.getCost()) {
//                            queue.set(queue.indexOf(jNode), iNode);
//                            queue.remove(iNode);
                            toRemoveFromQueue.add(jNode);
//                        } else {
//                            queue.remove(iNode); //if not, don't add to queue
//                            toRemoveFromQueue.add(iNode);

                        // if two nodes's parent have same state
                        // and they have the same cost --> same node
                        }else if(iNode.getParent().equals(jNode.getParent()) && iNode.getCost() == jNode.getCost()){
                            if(onlyOne)
                                toRemoveFromQueue.add(iNode); // remove duplicate node
                            onlyOne = true;
                        }
                    }
                }
            }

            i = queue.iterator();
            while(i.hasNext()){
                Node iNode = i.next();

//                if(iNode.equals()){
//                    toRemoveFromQueue.add()
//                }
                if(iNode.isExpanded())
                    totalExpandedNodes.add(iNode);

                if(iNode.getState()[2][0] == ' '){
//                    System.out.println("almost!");
                    if(iNode.equals(goalState))
                        System.out.println("woohoo");
                }
            }

            for (Node a : queue) {
                if(a.isExpanded())
                    toRemoveFromQueue.add(a);
            }
            queue.removeAll(toRemoveFromQueue);

            toRemoveFromQueue.clear();

            int[] queueCosts = new int[queue.size()];
            int x = 0;
            // find new node with lowest cost from root
            int lowestCost = Integer.MAX_VALUE;
            for (Node a : queue) {
                if(a.getCost() < lowestCost && !a.isExpanded()){
                    lowestCost = a.getCost();
                    node = a;
                }
                queueCosts[x++] = a.getCost();
            }
//            System.out.println(queue);
//            System.out.println(Arrays.toString(queueCosts));
//            System.out.println("______________________________\n");

            endNodeExpanded++;
        }
        */
    }

    private static Search queryUser(){

        String information = "";
//        information += "Game start position:";
//        information += startNode;
        information += "\nPlease choose a search strategy:\n";
        information += "1) Uniform cost search\n";
        information += "2) A * searh\n";

        System.out.println(information);

        Scanner userInput = new Scanner(System.in);
        String response = userInput.nextLine();

        switch (response.toLowerCase()){
            case("q"):
            case("quit"):
            case("exit"):
            case("stop"):
                System.exit(0);
                break;
        }
        int responseValue = -1;
        try{
            responseValue = Integer.parseInt(response);
            return Search.values()[responseValue - 1]; // e.g. Search.UNIFORM_COST;
        }catch(NumberFormatException e){
            System.out.println("Please enter a number, or type quit to exit");
            return queryUser(); // recursive call to this function
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Please enter either 1 or 2, or type quit to exit");
            return queryUser();
        }
    }
}
