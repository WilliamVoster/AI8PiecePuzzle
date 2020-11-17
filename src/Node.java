import java.util.ArrayList;
import java.util.Arrays;

public class Node implements Comparable {

    private char[][] state;
    private Node parent;
    private int cost = 0;
    private int heuristic = 0;
    private int combinedCost = 0;
    private ArrayList<Node> possiblePaths = new ArrayList<>();
    private boolean expanded = false;

    Node(char[][] state, Node parent, int cost){
        this.state = state;
        this.parent = parent;
        this.cost = cost;
    }

    Node(char[][] state){
        this(state, null, 0);
    }

    public char[][] getState(){return state;}
    public Node getParent(){return parent;}
    public int getCost(){return cost;}
    public int getHeuristic(){return heuristic;}
    public int getCombinedCost(){return combinedCost;}
    public ArrayList<Node> getPossiblePaths(){return possiblePaths;}
    public boolean isExpanded(){ return expanded; }

    public void setCost(int cost){this.cost = cost;}
    public void setParent(Node parent){this.parent = parent;}

    // Find all possible directions and store in 'possiblePaths'
    public void expand() {
        this.expanded = true;

        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {

                if(state[i][j] == ' '){
                    // has 2-4 options / directions to move the ' '

                    // copy and swap position of ' ' and 'n' into new Node
                    if( i > 0){
                        addChildNode(i, j, 'U');
                    }
                    if( i < 2){
                        addChildNode(i, j, 'D');
                    }
                    if( j > 0){
                        addChildNode(i, j, 'L');
                    }
                    if( j < 2){
                        addChildNode(i, j, 'R');
                    }
                }
            }
        }

//        return new ArrayList<>();
    }

    private char[][] copyState(){

        char[][] returnState = new char[3][3];

        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {
                returnState[i][j] = this.state[i][j];
            }
        }
        return returnState;
    }

    // in state space searching terminology this is the expanding of a node
    private void addChildNode(int i, int j, char direction){

        char tempChar;

        // copy and swap position of ' ' and 'n' into new Node
        char[][] tempState = copyState();

        switch (direction){
            case('U'):
            case('u'):{
                tempChar = state[i-1][j];
                tempState[i-1][j] = ' ';
                break;
            }
            case('D'):
            case('d'):{
                tempChar = state[i+1][j];
                tempState[i+1][j] = ' ';
                break;
            }
            case('L'):
            case('l'):{
                tempChar = state[i][j-1];
                tempState[i][j-1] = ' ';
                break;
            }
            case('R'):
            case('r'):{
                tempChar = state[i][j+1];
                tempState[i][j+1] = ' ';
                break;
            }
            default:
                throw new IllegalStateException("Unexpected direction: " + direction);
        }

        tempState[i][j] = tempChar;
        int newCost = cost + 1;
        possiblePaths.add(new Node(tempState, this, newCost)); // cost increases for every move

    }

    // compare if two nodes have the same state
    public boolean equals(Node other){ return Arrays.deepEquals(state, other.getState()); }


    /*
        1-numCorrect numbers
        1 - 3/9 = 2/3

        1 - (0/9 * posvalue)= 1
        posvalue :
            1 = middle
            2 = side
            4 = corner

        1 - (1/9 * 1) = 8/9 = 0.888  = 88  /100
        1 - (1/9 * 1.25)    = 0.8611 = 86  /100
        1 - (1/9 * 1.5)     = 0.8333 = 83  /100

        1 - (1/9 * 2)    = 0.7777 = 77  /100
        1 - (1/9 * 4)    = 0.5555 = 55  /100
        1 - (2/9 * 2)    = 0.5555 = 55  /100

        Cost ^^  / distance

*/
    public static final int CORNER = 4;
    public static final int SIDE = 2;
    public static final int MIDDLE = 1;
    public static final double A_NINETH = (double) 1 / (double) 9;

    // progress towards goalstate
    private double calcScore(State2D goal){
        double score = 0.0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(this.state[i][j] == goal.state[i][j]){
                    if( // corner
                        (i == 0 && (j == 0 || j == 2)) ||
                        (i == 2 && (j == 0 || j == 2))
                    ){
                        score += A_NINETH * CORNER;
                    }else if( // side
                        (j == 1 && (i == 0 || i == 2)) ||
                        (i == 1 && (j == 0 || j == 2))
                    ){
                        score += A_NINETH * SIDE;
                    }else if( // middle
                        (i == 1 && j == 1)
                    ){
                        score += A_NINETH * MIDDLE;
                    }else{
                        System.out.println("how did we get here?");
                    }
                }
            }
        }
        return score;// max is ~2.777
    }
    public void calcHeuristic(State2D goal){
        double score = calcScore(goal);// max is ~2.777
        score /= 2.777;
        score *= 100;
//        this.heuristic = (int)(score / (double)this.cost);
        this.heuristic = (int)(100 - score);
        this.combinedCost = this.heuristic + this.cost;
    }


    @Override
    public String toString() {

         return toSimpleString();
        /*
        //Arrays.deepToString(state)
        String returnString = "\n";
        for (char[] arr : state){
            returnString += Arrays.toString(arr) + "\n";
        };
        return returnString;*/
    }

    public String toSimpleString(){
        char[] returnArr = new char[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                returnArr[ i * 3 + j ] = state[i][j];
            }
        }
        return Arrays.toString(returnArr);
    }

    @Override
    public boolean equals(Object o){
        Node other = (Node) o;
        if(
            this.getState().equals(other.getState()) &&
            this.getCost() == other.getCost() &&
            this.getParent().equals(other.getParent())
        ){
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        Node other = (Node) o;
        if(this.equals(other)) return 0;
        return this.getCost() < other.getCost() ? -1 : 1;
    }
}
