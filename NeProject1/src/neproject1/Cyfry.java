/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neproject1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Paweł
 */
public class Cyfry extends javax.swing.JFrame {

    /**
     * Creates new form Cyfry
     */
     List<JButton> buttons = new ArrayList<>();
    public float[][] p_weight = new float [10][25]; //wagi pereceptronow
        int[] zero = {
        1, 1, 1, 1, 1,
        1, 0, 0, 0, 1,
        1, 0, 0, 0, 1,
        1, 0, 0, 0, 1,
        1, 1, 1, 1, 1};

    int[] jeden = {
        0, 0, 0, 0, 1,
        0, 0, 0, 0, 1,
        0, 0, 0, 0, 1,
        0, 0, 0, 0, 1,
        0, 0, 0, 0, 1,
    };

    int[] dwa = {
        1, 1, 1, 1, 1,
        0, 0, 0, 0, 1,
        1, 1, 1, 1, 1,
        1, 0, 0, 0, 0,
        1, 1, 1, 1, 1};

    int[] trzy = {
        1, 1, 1, 1, 1,
        0, 0, 0, 0, 1,
        1, 1, 1, 1, 1,
        0, 0, 0, 0, 1,
        1, 1, 1, 1, 1};

    int[] cztery = {
        1, 0, 0, 0, 1,
        1, 0, 0, 0, 1,
        1, 1, 1, 1, 1,
        0, 0, 0, 0, 1,
        0, 0, 0, 0, 1};

    int[] piec = {
        1, 1, 1, 1, 1,
        1, 0, 0, 0, 0,
        1, 1, 1, 1, 1,
        0, 0, 0, 0, 1,
        1, 1, 1, 1, 1};

    int[] szesc = {
        1, 1, 1, 1, 1,
        1, 0, 0, 0, 0,
        1, 1, 1, 1, 1,
        1, 0, 0, 0, 1,
        1, 1, 1, 1, 1};

    int[] siedem = {
        1, 1, 1, 1, 1,
        0, 0, 0, 0, 1,
        0, 0, 0, 0, 1,
        0, 0, 0, 0, 1,
        0, 0, 0, 0, 1,
    };

    int[] osiem = {
        1, 1, 1, 1, 1,
        1, 0, 0, 0, 1,
        1, 1, 1, 1, 1,
        1, 0, 0, 0, 1,
        1, 1, 1, 1, 1};

    int[] dziewiec = {
        1, 1, 1, 1, 1,
        1, 0, 0, 0, 1,
        1, 1, 1, 1, 1,
        0, 0, 0, 0, 1,
        1, 1, 1, 1, 1};
    
    public int [][] numbers = {zero.clone(),jeden.clone(),dwa.clone(),trzy.clone(),cztery.clone(),
        piec.clone(),szesc.clone(),siedem.clone(),osiem.clone(),dziewiec.clone()};
    public int[] inputs= 
    {    0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0
        
    };
    public int [][] answers=new int[10][10];
    public float[] w0=new float[10];
    public float[] bw0=new float[10];
    
    
    public Cyfry() {
        initComponents();
         CreateT();
         AddButtons();
      
    }
 
    
    public int perceptron(float[] weight,int[]data,float p0) // zwraca wynik perceptronu
    {
        float sum=0;
        
        for(int i=0;i<25;i++) 
        {
            sum+=weight[i]*data[i];
        }
        sum+=p0;
        
        if(sum>0)
            return 1;
        else
            return -1;
    }
    public int[] szum(int [] matrixtonoise)
    {
        int []r_matrix = new int [matrixtonoise.length];
        for(int i=0;i<matrixtonoise.length;i++)
        {
            r_matrix[i]=matrixtonoise[i];
                    float x=randFloat(0,1);
                    if(x<0.05f)
                    {
                        if(matrixtonoise[i]==0)
                            r_matrix[i]=1;
                            else
                            r_matrix[i]=0;
                    }
                    
        }
        return r_matrix;
    }
    public float[] lernperceptron(int np,float[] weight,int [][]answers,int [][]lernmatrix,int interatrion_n,float n)
    {
        double error; // wartosc bledu
        int lifetime=0;
        int b_lifetime=0;
        float[] b_weight=weight; // najepsze wagi do zwrocenie pod koniec algorytmu
        for(int i=0;i<interatrion_n;i++)
        {
            
            int index = randInt(0, 9);//losowanie przykładu uczącege
            int answer= answers[index][np]; // sprwdzam poprawna odpowiedz dla perceptronu //sprawdzone dobrze jest
            int [] noise;
            noise = szum(lernmatrix[index]);
            int O=perceptron(weight,noise,bw0[np]); //Wynik dla danego perceptronu //work
            error=answer-O;
                        
            if(error==0)
            {
                lifetime++;
                if(lifetime>b_lifetime) // zamiana najepszych znalezionych wag
                {
                    b_weight=weight; 
                    b_lifetime=lifetime;
                    bw0[np]=w0[np];
                }
            }
                else
                {
                
                        for(int x=0;x<25;x++) //aktualizacja wag gdy jest blad
                        {
                            weight[x] += n * error *lernmatrix[index][x];                                                  
                        }
                        w0[np] += n * error;
                        lifetime=0;             
                }
            
            
        }
        return b_weight; // zwracam wagi ktore "przetrwaly" najdluzej
    }
    public int randInt(int min,int max)
    {
        Random r = new Random();
        return r.nextInt((max - min) +1) + min;
    }
    public float randFloat(float min,float max)
    {
               Random rand = new Random();
               float result = rand.nextFloat() * (max - min) + min;

        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        p1 = new javax.swing.JButton();
        p3 = new javax.swing.JButton();
        p4 = new javax.swing.JButton();
        p5 = new javax.swing.JButton();
        p2 = new javax.swing.JButton();
        p8 = new javax.swing.JButton();
        p9 = new javax.swing.JButton();
        p10 = new javax.swing.JButton();
        p7 = new javax.swing.JButton();
        p6 = new javax.swing.JButton();
        p17 = new javax.swing.JButton();
        p16 = new javax.swing.JButton();
        p13 = new javax.swing.JButton();
        p14 = new javax.swing.JButton();
        p15 = new javax.swing.JButton();
        p12 = new javax.swing.JButton();
        p18 = new javax.swing.JButton();
        p19 = new javax.swing.JButton();
        p20 = new javax.swing.JButton();
        p11 = new javax.swing.JButton();
        p22 = new javax.swing.JButton();
        p21 = new javax.swing.JButton();
        p23 = new javax.swing.JButton();
        p24 = new javax.swing.JButton();
        p25 = new javax.swing.JButton();
        Check = new javax.swing.JButton();
        Learn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        p1.setBackground(new java.awt.Color(255, 255, 255));
        p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p1ActionPerformed(evt);
            }
        });

        p3.setBackground(new java.awt.Color(255, 255, 255));
        p3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p3ActionPerformed(evt);
            }
        });

        p4.setBackground(new java.awt.Color(255, 255, 255));
        p4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p4ActionPerformed(evt);
            }
        });

        p5.setBackground(new java.awt.Color(255, 255, 255));
        p5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p5ActionPerformed(evt);
            }
        });

        p2.setBackground(new java.awt.Color(255, 255, 255));
        p2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p2ActionPerformed(evt);
            }
        });

        p8.setBackground(new java.awt.Color(255, 255, 255));
        p8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p8ActionPerformed(evt);
            }
        });

        p9.setBackground(new java.awt.Color(255, 255, 255));
        p9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p9ActionPerformed(evt);
            }
        });

        p10.setBackground(new java.awt.Color(255, 255, 255));
        p10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p10ActionPerformed(evt);
            }
        });

        p7.setBackground(new java.awt.Color(255, 255, 255));
        p7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p7ActionPerformed(evt);
            }
        });

        p6.setBackground(new java.awt.Color(255, 255, 255));
        p6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p6ActionPerformed(evt);
            }
        });

        p17.setBackground(new java.awt.Color(255, 255, 255));
        p17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p17ActionPerformed(evt);
            }
        });

        p16.setBackground(new java.awt.Color(255, 255, 255));
        p16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p16ActionPerformed(evt);
            }
        });

        p13.setBackground(new java.awt.Color(255, 255, 255));
        p13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p13ActionPerformed(evt);
            }
        });

        p14.setBackground(new java.awt.Color(255, 255, 255));
        p14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p14ActionPerformed(evt);
            }
        });

        p15.setBackground(new java.awt.Color(255, 255, 255));
        p15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p15ActionPerformed(evt);
            }
        });

        p12.setBackground(new java.awt.Color(255, 255, 255));
        p12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p12ActionPerformed(evt);
            }
        });

        p18.setBackground(new java.awt.Color(255, 255, 255));
        p18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p18ActionPerformed(evt);
            }
        });

        p19.setBackground(new java.awt.Color(255, 255, 255));
        p19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p19ActionPerformed(evt);
            }
        });

        p20.setBackground(new java.awt.Color(255, 255, 255));
        p20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p20ActionPerformed(evt);
            }
        });

        p11.setBackground(new java.awt.Color(255, 255, 255));
        p11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p11ActionPerformed(evt);
            }
        });

        p22.setBackground(new java.awt.Color(255, 255, 255));
        p22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p22ActionPerformed(evt);
            }
        });

        p21.setBackground(new java.awt.Color(255, 255, 255));
        p21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p21ActionPerformed(evt);
            }
        });

        p23.setBackground(new java.awt.Color(255, 255, 255));
        p23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p23ActionPerformed(evt);
            }
        });

        p24.setBackground(new java.awt.Color(255, 255, 255));
        p24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p24ActionPerformed(evt);
            }
        });

        p25.setBackground(new java.awt.Color(255, 255, 255));
        p25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p25ActionPerformed(evt);
            }
        });

        Check.setText("Sprawdź");
        Check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckActionPerformed(evt);
            }
        });

        Learn.setText("Naucz");
        Learn.setActionCommand("");
        Learn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LearnActionPerformed(evt);
            }
        });

        jButton1.setText("Zaszum");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Wyczysc");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("2");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("3");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("4");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("1");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("0");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("8");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("9");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("7");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("6");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(Learn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Check, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(p6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(p7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(p9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(p16, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(p17, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p18, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(p19, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p20, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(p11, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(p12, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p13, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(p14, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p15, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(p21, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(p22, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p23, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(p24, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p25, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(p4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(p5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton5)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Learn, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Check, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton10)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(p6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p8, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p9, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p10, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(p12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(p11, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(p13, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(p14, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(p15, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(p16, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p18, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p20, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p17, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(p21, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p23, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p24, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p25, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p22, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(40, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p1ActionPerformed
        // TODO add your handling code here:
                if (p1.getBackground().equals(Color.white)) {
            p1.setBackground(Color.red);
            inputs[0]=1;
        } else {
            p1.setBackground(Color.white);
            inputs[0]=0;
        }
    }//GEN-LAST:event_p1ActionPerformed

    private void p3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p3ActionPerformed
        // TODO add your handling code here:
                        if (p3.getBackground().equals(Color.white)) {
            p3.setBackground(Color.red);
            inputs[2]=1;
        } else {
            p3.setBackground(Color.white);
            inputs[2]=0;
        }
    }//GEN-LAST:event_p3ActionPerformed

    private void p4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p4ActionPerformed
        // TODO add your handling code here:
                        if (p4.getBackground().equals(Color.white)) {
            p4.setBackground(Color.red);
            inputs[3]=1;
        } else {
            p4.setBackground(Color.white);
            inputs[3]=0;
        }
    }//GEN-LAST:event_p4ActionPerformed

    private void p5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p5ActionPerformed
        // TODO add your handling code here:
        if (p5.getBackground().equals(Color.white)) {
            p5.setBackground(Color.red);
            inputs[4]=1;
        } else {
            p5.setBackground(Color.white);
            inputs[4]=0;
        }
    }//GEN-LAST:event_p5ActionPerformed

    private void p2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p2ActionPerformed
        // TODO add your handling code here:
                if (p2.getBackground().equals(Color.white)) {
            p2.setBackground(Color.red);
            inputs[1]=1;
        } else {
            p2.setBackground(Color.white);
            inputs[1]=0;
        }
    }//GEN-LAST:event_p2ActionPerformed

    private void p8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p8ActionPerformed
        // TODO add your handling code here:
                if (p8.getBackground().equals(Color.white)) {
            p8.setBackground(Color.red);
            inputs[7]=1;
        } else {
                    inputs[7]=0;
            p8.setBackground(Color.white);
        }
    }//GEN-LAST:event_p8ActionPerformed

    private void p9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p9ActionPerformed
        // TODO add your handling code here:
                if (p9.getBackground().equals(Color.white)) {
            p9.setBackground(Color.red);
            inputs[8]=1;
        } else {
            p9.setBackground(Color.white);
            inputs[8]=0;
        }
    }//GEN-LAST:event_p9ActionPerformed

    private void p10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p10ActionPerformed
        // TODO add your handling code here:
                if (p10.getBackground().equals(Color.white)) {
            p10.setBackground(Color.red);
            inputs[9]=1;
        } else {
            p10.setBackground(Color.white);
            inputs[9]=0;
        }
    }//GEN-LAST:event_p10ActionPerformed

    private void p7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p7ActionPerformed
        // TODO add your handling code here:
                if (p7.getBackground().equals(Color.white)) {
            p7.setBackground(Color.red);
            inputs[6]=1;
        } else {
            p7.setBackground(Color.white);
            inputs[6]=0;
        }
    }//GEN-LAST:event_p7ActionPerformed

    private void p6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p6ActionPerformed
        // TODO add your handling code here:
                if (p6.getBackground().equals(Color.white)) {
            p6.setBackground(Color.red);
            inputs[5]=1;
        } else {
            p6.setBackground(Color.white);
            inputs[5]=0;
        }
    }//GEN-LAST:event_p6ActionPerformed

    private void p17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p17ActionPerformed
        // TODO add your handling code here:
                if (p17.getBackground().equals(Color.white)) {
            p17.setBackground(Color.red);
            inputs[16]=1;
        } else {
            p17.setBackground(Color.white);
            inputs[16]=0;
        }
    }//GEN-LAST:event_p17ActionPerformed

    private void p16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p16ActionPerformed
        // TODO add your handling code here:
                if (p16.getBackground().equals(Color.white)) {
            p16.setBackground(Color.red);
            inputs[15]=1;
        } else {
            p16.setBackground(Color.white);
            inputs[15]=0;
        }
    }//GEN-LAST:event_p16ActionPerformed

    private void p13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p13ActionPerformed
        // TODO add your handling code here:
                if (p13.getBackground().equals(Color.white)) {
            p13.setBackground(Color.red);
            inputs[12]=1;
        } else {
            p13.setBackground(Color.white);
            inputs[12]=0;
        }
    }//GEN-LAST:event_p13ActionPerformed

    private void p14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p14ActionPerformed
        // TODO add your handling code here:
        if (p14.getBackground().equals(Color.white)) {
            p14.setBackground(Color.red);
            inputs[13]=1;
        } else {
            p14.setBackground(Color.white);
            inputs[13]=0;
        }
    }//GEN-LAST:event_p14ActionPerformed

    private void p15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p15ActionPerformed
        // TODO add your handling code here:
        if (p15.getBackground().equals(Color.white)) {
            p15.setBackground(Color.red);
            inputs[14]=1;
        } else {
            p15.setBackground(Color.white);
            inputs[14]=0;
        }
    }//GEN-LAST:event_p15ActionPerformed

    private void p12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p12ActionPerformed
        // TODO add your handling code here:
        if (p12.getBackground().equals(Color.white)) {
            p12.setBackground(Color.red);
            inputs[11]=1;
        } else {
            p12.setBackground(Color.white);
            inputs[11]=0;
        }
    }//GEN-LAST:event_p12ActionPerformed

    private void p18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p18ActionPerformed
        // TODO add your handling code here:
         if (p18.getBackground().equals(Color.white)) {
            p18.setBackground(Color.red);
            inputs[17]=1;
        } else {
            p18.setBackground(Color.white);
            inputs[17]=0;
        }
    }//GEN-LAST:event_p18ActionPerformed

    private void p19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p19ActionPerformed
        // TODO add your handling code here:
        if (p19.getBackground().equals(Color.white)) {
            p19.setBackground(Color.red);
            inputs[18]=1;
        } else {
            p19.setBackground(Color.white);
            inputs[18]=0;
        }
    }//GEN-LAST:event_p19ActionPerformed

    private void p20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p20ActionPerformed
        // TODO add your handling code here:
        if (p20.getBackground().equals(Color.white)) {
            p20.setBackground(Color.red);
            inputs[19]=1;
        } else {
            p20.setBackground(Color.white);
            inputs[19]=0;
        }
    }//GEN-LAST:event_p20ActionPerformed

    private void p11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p11ActionPerformed
        // TODO add your handling code here:
        if (p11.getBackground().equals(Color.white)) {
            p11.setBackground(Color.red);
            inputs[10]=1;
        } else {
            p11.setBackground(Color.white);
            inputs[10]=0;
        }
    }//GEN-LAST:event_p11ActionPerformed

    private void p22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p22ActionPerformed
        // TODO add your handling code here:
        if (p22.getBackground().equals(Color.white)) {
            p22.setBackground(Color.red);
            inputs[21]=1;
        } else {
            p22.setBackground(Color.white);
            inputs[21]=0;
        }
    }//GEN-LAST:event_p22ActionPerformed

    private void p21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p21ActionPerformed
        // TODO add your handling code here:
        if (p21.getBackground().equals(Color.white)) {
            p21.setBackground(Color.red);
            inputs[20]=1;
        } else {
            p21.setBackground(Color.white);
            inputs[20]=0;
        }
    }//GEN-LAST:event_p21ActionPerformed

    private void p23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p23ActionPerformed
        // TODO add your handling code here:
        if (p23.getBackground().equals(Color.white)) {
            p23.setBackground(Color.red);
            inputs[22]=1;
        } else {
            p23.setBackground(Color.white);
            inputs[22]=0;
        }
    }//GEN-LAST:event_p23ActionPerformed

    private void p24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p24ActionPerformed
        // TODO add your handling code here:
        if (p24.getBackground().equals(Color.white)) {
            p24.setBackground(Color.red);
            inputs[23]=1;
        } else {
            p24.setBackground(Color.white);
            inputs[23]=0;
        }
    }//GEN-LAST:event_p24ActionPerformed

    private void p25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p25ActionPerformed
        // TODO add your handling code here:
        if (p25.getBackground().equals(Color.white)) {
            p25.setBackground(Color.red);
            inputs[24]=1;
        } else {
            p25.setBackground(Color.white);
            inputs[24]=0;
        }
    }//GEN-LAST:event_p25ActionPerformed

    private void LearnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LearnActionPerformed
        // TODO add your handling code here:
          
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<25;j++)
            {
                p_weight[i][j]=(float) (randFloat(0, 1)-0.5f );
            }
            w0[i]=(float) (randFloat(0, 1)-0.5f );
            bw0[i]=w0[i];
            
            // (float[] weight,int [][]answers,float w0,int [][]lernmatrix,int interatrion_n,float n
            p_weight[i]=lernperceptron(i,p_weight[i],answers,numbers,100000,0.2f); //uczymy perceptron i numer - wagi, odpowiedzi
            
        }
        JOptionPane.showMessageDialog(null,"Koniec nauki");
        
    }//GEN-LAST:event_LearnActionPerformed

    private void CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckActionPerformed
        // TODO add your handling code here:
        
        String Result="";
        int p_result;

        for(int i=0;i<10;i++)
        {
            p_result=perceptron(p_weight[i],inputs,bw0[i]);
            if(p_result==1)
            {
                Result+=i;
            }
        }
        if("".equals(Result))
        {
            JOptionPane.showMessageDialog(null,"Nie znaleziono liczby");
        }
        else
        {
           JOptionPane.showMessageDialog(null,"Jest to "+Result); 
        }
            
    }//GEN-LAST:event_CheckActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        inputs=szum(inputs);
                int i=0;
                for (JButton b : buttons) {
                    if(inputs[i]==1)
                    b.setBackground(Color.red);
                    else
                    b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int i=0;
                        for (JButton b : buttons) {
                    b.setBackground(Color.white);
                    inputs[i]=0;
                    i++;
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
                inputs=osiem;
        int i=0;
                for (JButton b : buttons) {
                    if(osiem[i]==1)
                        b.setBackground(Color.red);
                    else
                        b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        inputs=zero;
        int i=0;
                for (JButton b : buttons) {
                    if(zero[i]==1)
                        b.setBackground(Color.red);
                    else
                        b.setBackground(Color.white);
                    i++;
        }
       
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
                inputs=jeden;
        int i=0;
                for (JButton b : buttons) {
                    if(jeden[i]==1)
                    b.setBackground(Color.red);
                    else
                    b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
                inputs=dwa;
        int i=0;
                for (JButton b : buttons) {
                    if(dwa[i]==1)
                    b.setBackground(Color.red);
                    else
                    b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
                inputs=trzy;
        int i=0;
                for (JButton b : buttons) {
                    if(trzy[i]==1)
                    b.setBackground(Color.red);
                    else
                    b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
                inputs=cztery;
        int i=0;
                for (JButton b : buttons) {
                        if(cztery[i]==1)
                    b.setBackground(Color.red);
                    else
                        b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
                inputs=piec;
        int i=0;
                for (JButton b : buttons) {
                    if(piec[i]==1)
                    b.setBackground(Color.red);
                    else
                    b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
                inputs=szesc;
        int i=0;
                for (JButton b : buttons) {
                    if(szesc[i]==1)
                    b.setBackground(Color.red);
                    else
                    b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
                inputs=siedem;
        int i=0;
                for (JButton b : buttons) {
                    if(siedem[i]==1)
                    b.setBackground(Color.red);
                    else
                    b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
                inputs=dziewiec;
        int i=0;
                for (JButton b : buttons) {
                    if(dziewiec[i]==1)
                    b.setBackground(Color.red);
                    else
                    b.setBackground(Color.white);
                    i++;
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cyfry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cyfry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cyfry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cyfry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Cyfry().setVisible(true);
        });
    }
    public void CreateT()
    {
        for(int i=0;i<25;i++)
        {
            inputs[i]=0;
        }
        for(int i=0;i<10;i++)
            {
                for(int j=0;j<10;j++)
                    {
                        if(i==j)
                                answers[i][j]=1;                            
                        else
                             answers[i][j]=-1;
            }
        }
    }
    public void AddButtons()
    {
        buttons.add(p1);
        buttons.add(p2);
        buttons.add(p3);
        buttons.add(p4);
        buttons.add(p5);
        buttons.add(p6);
        buttons.add(p7);
        buttons.add(p8);
        buttons.add(p9);
        buttons.add(p10);
        buttons.add(p11);
        buttons.add(p12);
        buttons.add(p13);
        buttons.add(p14);
        buttons.add(p15);
        buttons.add(p16);
        buttons.add(p17);
        buttons.add(p18);
        buttons.add(p19);
        buttons.add(p20);
        buttons.add(p21);
        buttons.add(p22);
        buttons.add(p23);
        buttons.add(p24);
        buttons.add(p25);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Check;
    private javax.swing.JButton Learn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JButton p1;
    private javax.swing.JButton p10;
    private javax.swing.JButton p11;
    private javax.swing.JButton p12;
    private javax.swing.JButton p13;
    private javax.swing.JButton p14;
    private javax.swing.JButton p15;
    private javax.swing.JButton p16;
    private javax.swing.JButton p17;
    private javax.swing.JButton p18;
    private javax.swing.JButton p19;
    private javax.swing.JButton p2;
    private javax.swing.JButton p20;
    private javax.swing.JButton p21;
    private javax.swing.JButton p22;
    private javax.swing.JButton p23;
    private javax.swing.JButton p24;
    private javax.swing.JButton p25;
    private javax.swing.JButton p3;
    private javax.swing.JButton p4;
    private javax.swing.JButton p5;
    private javax.swing.JButton p6;
    private javax.swing.JButton p7;
    private javax.swing.JButton p8;
    private javax.swing.JButton p9;
    // End of variables declaration//GEN-END:variables
}
