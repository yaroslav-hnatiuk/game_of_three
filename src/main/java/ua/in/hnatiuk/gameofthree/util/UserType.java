package ua.in.hnatiuk.gameofthree.util;

public enum UserType {
    HUMAN, AI;

    public static UserType getUserType(int type) throws RuntimeException{
        switch (type){
            case 0:
                return UserType.HUMAN;
            case 1:
                return UserType.AI;
            default:
                throw new RuntimeException("wrong player value");
        }
    }
}
