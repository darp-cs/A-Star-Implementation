/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a;

import java.util.Scanner;
import java.util.*;
import java.util.Hashtable;
import java.lang.Math;
import java.util.Arrays;

/**
 *
 * @author diegorodriguez
 */
public class A {

    /**
     * @param args the command line arguments
     */
    private Node env[][];
    private PriorityQueue oL;
    private Hashtable cL;
    private int block;
    private Node start;
    private Node goal;
    private boolean over;
    private boolean goalReached;
    private Node current;
    private LinkedList<Node> rev;

    public A() {
        env = new Node[15][15];
        oL = new PriorityQueue(225, new FScoreComparator());
        cL = new Hashtable();
        block = 0;
        start = null;
        goal = null;
        over = false;
        goalReached = false;
        current = null;
    }

    public void setUp() {
        block = 0;
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                env[row][col] = new Node(row, col, 0);
            }
        }

        while (block < 23) {
            Random randRow = new Random();
            Random randCol = new Random();
            int row = randRow.nextInt(15);
            int col = randCol.nextInt(15);
            while (env[row][col].getT() != 1) {
                env[row][col].setT(1);
                block++;
            }
        }
    }

    public void setStart(int row, int col) {
        start = env[row][col];
    }

    public void setGoal(int row, int col) {
        goal = env[row][col];
    }

    public void heu() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                int temp = Math.abs(row - goal.getRow()) * 10;
                temp += Math.abs(col - goal.getCol()) * 10;
                env[row][col].setH(temp);
            }
        }
    }

    public void search(Node curr) {
        //left
        if (curr.getRow() > 0 && env[curr.getRow() - 1][curr.getCol()].getT() != 1) {
            Node settingE = env[curr.getRow() - 1][curr.getCol()];
            if (oL.contains(settingE)) {
                int temp = curr.getG() + 10;
                if (temp < settingE.getG()) {
                    settingE.setG(temp);
                    settingE.setParent(curr);
                }
                oL.remove(settingE);
            } else {
                settingE.setG(curr.getG() + 10);
                settingE.setParent(curr);
            }
            settingE.setF();
            oL.add(settingE);

        }
        //right
        if (curr.getRow() < 14 && env[curr.getRow() + 1][curr.getCol()].getT() != 1) {
            Node settingW = env[curr.getRow() + 1][curr.getCol()];
            if (oL.contains(settingW)) {
                int temp = curr.getG() + 10;
                if (temp < settingW.getG()) {
                    settingW.setG(temp);
                    settingW.setParent(curr);
                }
                oL.remove(settingW);
            } else {
                settingW.setG(curr.getG() + 10);
                settingW.setParent(curr);
            }
            settingW.setF();
            oL.add(settingW);
        }
        //up
        if (curr.getCol() > 0 && env[curr.getRow()][curr.getCol() - 1].getT() != 1) {
            Node settingN = env[curr.getRow()][curr.getCol() - 1];
            if (oL.contains(settingN)) {
                int temp = curr.getG() + 10;
                if (temp < settingN.getG()) {
                    settingN.setG(temp);
                    settingN.setParent(curr);
                }
                oL.remove(settingN);
            } else {
                settingN.setG(curr.getG() + 10);
                settingN.setParent(curr);
            }
            settingN.setF();
            oL.add(settingN);
        }
        //down
        if (curr.getCol() < 14 && env[curr.getRow()][curr.getCol() + 1].getT() != 1) {
            Node settingS = env[curr.getRow()][curr.getCol() + 1];
            if (oL.contains(settingS)) {
                int temp = curr.getG() + 10;
                if (temp < settingS.getG()) {
                    settingS.setG(temp);
                    settingS.setParent(curr);
                }
                oL.remove(settingS);
            } else {
                settingS.setG(curr.getG() + 10);
                settingS.setParent(curr);
            }
            settingS.setF();
            oL.add(settingS);
        }
        //diag up left
        if (curr.getRow() > 0 && curr.getCol() > 0 && env[curr.getRow() - 1][curr.getCol() - 1].getT() != 1) {
            Node settingNE = env[(curr.getRow() - 1)][(curr.getCol() - 1)];
            if (oL.contains(settingNE)) {
                int temp = curr.getG() + 14;
                if (temp < settingNE.getG()) {
                    settingNE.setG(temp);
                    settingNE.setParent(curr);
                }
                oL.remove(settingNE);
            } else {
                settingNE.setG(curr.getG() + 14);
                settingNE.setParent(curr);
            }
            settingNE.setF();
            oL.add(settingNE);
        }
        //diag right up
        if (curr.getRow() < 14 && curr.getCol() > 0 && env[curr.getRow() + 1][curr.getCol() - 1].getT() != 1) {
            Node settingNW = env[(curr.getRow() + 1)][(curr.getCol() - 1)];
            if (oL.contains(settingNW)) {
                int temp = curr.getG() + 14;
                if (temp < settingNW.getG()) {
                    settingNW.setG(temp);
                    settingNW.setParent(curr);
                }
                oL.remove(settingNW);
            } else {
                settingNW.setG(curr.getG() + 14);
                settingNW.setParent(curr);
            }
            settingNW.setF();
            oL.add(settingNW);
        }
        //diag right down
        if (curr.getCol() < 14 && curr.getRow() < 14 && env[curr.getRow() + 1][curr.getCol() + 1].getT() != 1) {
            Node settingSW = env[(curr.getRow() + 1)][(curr.getCol() + 1)];
            if (oL.contains(settingSW)) {
                int temp = curr.getG() + 14;
                if (temp < settingSW.getG()) {
                    settingSW.setG(temp);
                    settingSW.setParent(curr);
                }
                oL.remove(settingSW);
            } else {
                settingSW.setG(curr.getG() + 14);
                settingSW.setParent(curr);
            }
            settingSW.setF();
            oL.add(settingSW);
        }
        //diag left down
        if (curr.getRow() > 0 && curr.getCol() < 14 && env[curr.getRow() - 1][curr.getCol() + 1].getT() != 1) {
            Node settingSE = env[(curr.getRow() - 1)][(curr.getCol() + 1)];
            if (oL.contains(settingSE)) {
                int temp = curr.getG() + 14;
                if (temp < settingSE.getG()) {
                    settingSE.setG(temp);
                    settingSE.setParent(curr);
                }
                oL.remove(settingSE);
            } else {
                settingSE.setG(curr.getG() + 14);
                settingSE.setParent(curr);
            }
            settingSE.setF();
            oL.add(settingSE);
        }
    }

    public LinkedList path(Node trace) {
        LinkedList<Node> solution = new LinkedList();
        if (trace.getParent() == null) {
            rev.add(trace);

            for (int lim = rev.size() - 1; lim >= 0; lim--) {

                solution.add(rev.get(lim));
            }
            for (int row = 0; row < 15; row++) {
                for (int col = 0; col < 15; col++) {
                    if (solution.contains(env[row][col])) {
                        System.out.print("*\t");
                    } else if (env[row][col].getT() == 1) {
                        System.out.print("X\t");
                    } else if (env[row][col] == start) {
                        System.out.print("S\t");
                    } else if (env[row][col] == goal) {
                        System.out.print("G\t");
                    } else {
                        System.out.print("O\t");
                    }
                }
                System.out.println();
            }
            return solution;
        }
        rev.add(trace);
        return path(trace.getParent());

    }

    public void run() {
        while (!over) {
            rev = new LinkedList();
            setUp();
            System.out.println("Enter a starting spot: row and column");
            Scanner inRow = new Scanner(System.in);
            Scanner inCol = new Scanner(System.in);
            Scanner choose = new Scanner(System.in);
            int cRow = inRow.nextInt();
            int cCol = inCol.nextInt();
            setStart(cRow, cCol);
            System.out.println("Enter a goal spot: row and column");
            cRow = inRow.nextInt();
            cCol = inCol.nextInt();
            setGoal(cRow, cCol);
            oL.add(start);
            heu();

            for (int row = 0; row < 15; row++) {
                for (int col = 0; col < 15; col++) {
                    if (env[row][col].getT() == 1) {
                        System.out.print("X\t");
                    } else if (env[row][col] == start) {
                        System.out.print("S\t");
                    } else if (env[row][col] == goal) {
                        System.out.print("G\t");
                    } else {
                        System.out.print("O\t");
                    }
                }
                System.out.println();
            }

            start.setF();
            while (!goalReached || !oL.isEmpty()) {
                current = (Node) oL.poll();
                if (current.equals(goal)) {
                    goalReached = true;
                    System.out.println("Goal Reached");
                    break;
                }
                search(current);
                cL.put(current, "Used");
                current.setT(1);

            }
            if (goalReached) {
                System.out.println("Game solved");
                System.out.println("Here is your path");
                path(current);

            }
            if (oL.isEmpty()) {
                System.out.println("Open list is empty: Ran out of moves");
            }
            System.out.println("Would you like to go again?");
            System.out.println("yes or no ");
            String yn = choose.nextLine();
            if (yn.equalsIgnoreCase("no")) {
                over = true;
            }
        }
    }

    private class FScoreComparator implements
            Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.getF() - o2.getF();
        }
    }

}
