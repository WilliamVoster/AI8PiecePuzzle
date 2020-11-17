public class State1D implements Comparable {

    public char[] state;

    State1D(char[] state){
        this.state = state;
    }

//    public char[] get(){
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
