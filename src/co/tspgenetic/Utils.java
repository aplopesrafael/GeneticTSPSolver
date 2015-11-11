/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.tspgenetic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.mechanism.utils.MersenneTwisterFast;

public class Utils {

    public static List<int[]> EUC2D_TSPLIBFileToArrayList(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            List<int[]> result = new ArrayList<int[]>();
            for (int i = 0; i < 6; i++) {
                br.readLine();
            }
            String line;
            while((line = br.readLine())!= null ) {
                if (line.equals("EOF")) {
                    break;
                }
                int[] temp = new int[2];
                temp[0] = Double.valueOf(line.split(" ")[1]).intValue();
                temp[1] = Double.valueOf(line.split(" ")[2]).intValue();
                result.add(temp);
            }
            
            return result;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
        return null;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getDistanceMatrixFromFile(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            int numCities = Integer.parseInt(br.readLine().split(":")[1]);
            Double startCity = Double.parseDouble(br.readLine().split(":")[1]);
            br.readLine();
            String line;
            //int i = 0;
            int[][] distances = new int[numCities][numCities];
            for(int i = 0; i < numCities; i++) {
                line = br.readLine();
                String[] ds = line.split(",");
                int[] temp = new int[numCities];
                for (int j = 0; j < numCities; j++) {
                	temp[j] = Integer.parseInt(ds[j]);
                }
                distances[i] = temp;
            }
			Map result = new HashMap();
            result.put("distances", distances);
            result.put("startCity", startCity);
            return result;            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
        return null;
    }

    public static int[][] getDistanceMatrixFromCoords(List<int[]> coords) {
        int[][] distanceMatrix = new int[coords.size()][coords.size()];
        for (int i = 0; i < coords.size(); i++) {
            for (int j = 0; j < coords.size(); j++) {
                if (i==j) {
                    distanceMatrix[i][j] = 0;
                }else{
                    //Euclidean distance
                    int dx = coords.get(i)[0] - coords.get(j)[0];
                    int dy = coords.get(i)[1] - coords.get(j)[1];
                    //double d = Math.sqrt(Math.pow(coords.get(i)[0] - coords.get(j)[0], 2) + Math.pow(coords.get(i)[1] - coords.get(j)[1], 2));
                    double d = Math.sqrt(dx*dx + dy*dy);
                    distanceMatrix[i][j] = (int) (d+0.5);
                }
            }
        }
        /*for (int i = 0; i < distanceMatrix.length; i++) {
        System.out.println(Arrays.toString(distanceMatrix[i]));
        }*/
        return distanceMatrix;
    }


    public static int[] getRandomPath(int[][] distanceMatrix) {
        List<Integer> path = new ArrayList<Integer>();
        int[] p = new int[distanceMatrix.length];
        Random r = new Random();
        for (int i = 0; i < distanceMatrix.length;) {
            int x;
            if (!path.contains((x = r.nextInt(distanceMatrix.length)))) {
                path.add(x);
                i++;
            }

        }
        for (int i = 0; i < distanceMatrix.length; i++) {
            p[i] = path.get(i);
        }
        return p;
    }

    public static int[] getPath(int[][] distanceMatrix) {
        int[] p = new int[distanceMatrix.length];
        for (int i = 0; i < distanceMatrix.length; i++) {
            p[i] = i;
        }
        return p;
    }

    public static int evaluatePath(int[] path, int[][]M){
        int costo = 0;
        //System.out.println(M[0].length);
        for (int i = 0; i < path.length-1; i++) {
            //System.out.println(i);
            costo += M[path[i]-1][path[i+1]-1];
        }
        costo += M[path[path.length-1]][path[0]];
        return costo;
    }

    public static int[] getPathFromTspLibFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
            List<Integer> path = new ArrayList<Integer>();
            for (int i = 0; i < 5; i++) {
                br.readLine();
            }
            String line;
            while((line = br.readLine())!= null ) {
                if (line.equals("-1")) {
                    break;
                }
                path.add(Integer.parseInt(line));
            }
            /*for (int i = 0; i < result.size(); i++) {
            System.out.println(Arrays.toString(result.get(i)));
            }*/
            //System.out.println(result.toString());
            int[] result = new int[path.size()];
            int i = 0;
            for(Integer city : path) {
                result[i] = city;
                i++;
            }
            return result;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
        return null;
    }
    public static double getMTRandom() {
        return new MersenneTwisterFast().nextDouble();
    }

    public static MersenneTwisterFast getMTInstance() {
        return new MersenneTwisterFast();
    }

    public static Object getInstanceByReflection(String qName) {
        Object o = null;
        try {
            o = Class.forName(qName).newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }

    public static List<Double> doubleListDeepCopy(List<Double> original) {
        List<Double> copy = new ArrayList<Double>();
        for (int i=0; i<original.size();i++) {
            copy.add(original.get(i).doubleValue());
        }
        return copy;
    }
    
    public static double getEuclideanDistance(List<Double> p1, List<Double> p2) {
        double result = 0d;
        for (int i = 0; i < p1.size(); i++) {
            result += Math.pow((p1.get(i)-p2.get(i)), 2);
        } 
        return Math.sqrt(result);
    }
}
