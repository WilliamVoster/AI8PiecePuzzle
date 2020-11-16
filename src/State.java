public class State implements Comparable {

    private char[][] state;

    State(char[][] state){
        this.state = state;
    }

    public char[][] get(){
        return state;
    }

    @Override
    public boolean equals(Object o){
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
