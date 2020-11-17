public class State2D implements Comparable {

    public char[][] state;

    State2D(char[][] state){
        this.state = state;
    }

//    public char[][] get(){
//        return state;
//    }

    @Override
    public boolean equals(Object o){
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
