package org.example;

//Player 類別代表一位遊戲玩家，包含名字與所屬陣營（X 或 O）
public class Player {
    String name;   // 玩家名稱
    String side;   // "X" or "O"
    //建構子：建立一位玩家並指定其名稱與陣營
    public Player(String name, String side) {
        this.name = name;
        this.side = side;
    }
}


