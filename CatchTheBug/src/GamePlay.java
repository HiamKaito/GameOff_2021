
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;

public class GamePlay extends javax.swing.JFrame {

    public class MyKeyListener extends KeyAdapter {

        public void keyPressed(KeyEvent evt) {
            if (evt.getKeyCode() == KeyEvent.VK_A || evt.getKeyCode() == KeyEvent.VK_LEFT) {
                System.out.println("KEY trai");
                BugMoving("LEFT");
            }
            if (evt.getKeyCode() == KeyEvent.VK_S || evt.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println("KEY xuong");
                BugMoving("DOWN");
            }
            if (evt.getKeyCode() == KeyEvent.VK_D || evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                System.out.println("KEY phai");
                BugMoving("RIGHT");
            }
            if (evt.getKeyCode() == KeyEvent.VK_W || evt.getKeyCode() == KeyEvent.VK_UP) {
                System.out.println("KEY len");
                BugMoving("UP");
            }
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                System.out.println("KEY len");
                BugMoving("SPACE");
            }
        }
    }

    public GamePlay() {
        initComponents();

        list = new boolean[lenght][lenght];
        listLabel = new JLabel[lenght][lenght];

        lblBug = new JLabel();

        createList();

        randomTrap();

        init();

        this.addKeyListener(new MyKeyListener());

    }

    public int lenght = 11;
    public boolean[][] list;
    public JLabel[][] listLabel;
    public JLabel lblBug;
    public int randomTrap = 50;

    public void init() {
        lblBug.setBounds(310, 310, 50, 50);
        lblBug.setBackground(Color.RED);
        lblBug.setOpaque(true);

        jPanel1.add(lblBug);

        int posX = 10, posY = 10;
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                if (!list[i][j]) {
                    JLabel lbl = new JLabel();

                    lbl.setBounds(posX, posY, 50, 50);
                    lbl.setBackground(Color.GREEN);
                    lbl.setName(i + ";" + j);

                    jPanel1.add(lbl);
                    posX += 60;
                } else {
                    JLabel lbl = new JLabel();

                    lbl.setBounds(posX, posY, 50, 50);
                    lbl.setBackground(Color.GREEN);
                    lbl.setOpaque(true);
                    lbl.setName(i + ";" + j + ";" + posX + ";" + posY + ";enable");

//                    lbl.addMouseListener(new MouseAdapter() {
//                        public void mouseClicked(MouseEvent evt) {
//                            eventOnClick_Label(lbl);
//                        }
//                    });
                    listLabel[i][j] = lbl;
                    jPanel1.add(lbl);
                    posX += 60;
                }
            }
            posX = 10;
            posY += 60;
        }
    }

    public void eventOnClick_Label(JLabel lbl) {
        String[] arr = lbl.getName().split(";");

        list[Integer.parseInt(arr[0])][Integer.parseInt(arr[1])] = false;
        listLabel[Integer.parseInt(arr[0])][Integer.parseInt(arr[1])].setVisible(false);

        lbl.setName(arr[0] + ";" + arr[1] + ";" + arr[2] + ";" + arr[3] + ";disable");

        System.out.println(lbl.getName());
        System.out.println(lbl.getX() + " " + lbl.getY());
    }

    public void randomTrap() {
        int i = 0;

        Random rand = new Random();
        do {

            int a = rand.nextInt((10 - 0) + 1) + 0;
            int b = rand.nextInt((10 - 0) + 1) + 0;

            //!(a == 5 && b == 5)
            //(a != 5 && b != 5)
            if (list[a][b] && !(a == 5 && b == 5)) {
                list[a][b] = false;
                i++;
            }

        } while (i < randomTrap);
    }

    public void createList() {
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                list[i][j] = true;
            }
        }
    }

    public void BugMoving(String key) {
//        lblBug.setBounds(420, 310, 50, 50);

//        Get pos
        int posX = 0, posY = 0;
        int geti = 0, getj = 0;

        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                try {
                    String[] arr = listLabel[i][j].getName().split(";");

                    posX = Integer.parseInt(arr[2]);
                    posY = Integer.parseInt(arr[3]);

                    if (lblBug.getX() == posX && lblBug.getY() == posY
                            && arr[4].equals("enable")) {
                        geti = i;
                        getj = j;

                        i = 100;
                        break;
                    }
                } catch (Exception e) {
                }
            }
        }

        switch (key) {
            case "LEFT":
                if (isGoable(geti, getj - 1)) {
                    lblBug.setBounds(posX - 60, posY, 50, 50);
                    disable(geti, getj);
                }
                break;
            case "RIGHT":
                if (isGoable(geti, getj + 1)) {
                    lblBug.setBounds(posX + 60, posY, 50, 50);
                    disable(geti, getj);
                }
                break;
            case "DOWN":
                if (isGoable(geti + 1, getj)) {
                    lblBug.setBounds(posX, posY + 60, 50, 50);
                    disable(geti, getj);
                }
                break;
            case "UP":
                if (isGoable(geti - 1, getj)) {
                    lblBug.setBounds(posX, posY - 60, 50, 50);
                    disable(geti, getj);
                }
                break;
            case "SPACE":
                GamePlay a = new GamePlay();
                a.setVisible(true);
                this.setVisible(false);
                break;
        }

        int i = 0;
        int a, b;
        Random rand = new Random();
        do {

            a = rand.nextInt((10 - 0) + 1) + 0;
            b = rand.nextInt((10 - 0) + 1) + 0;

            if (list[a][b]) {
                if (!(a == geti && a == getj)) {
                    list[a][b] = false;
                    i++;
                }
            }

        } while (i == 0);
        disable(a, b);
    }

    public boolean isGoable(int i, int j) {

        try {
            String[] arr = listLabel[i][j].getName().split(";");

            if (arr[4].equals("enable")) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "You win");
            GamePlay a = new GamePlay();
            a.setVisible(true);
            this.setVisible(false);
            return false;
        }
        return false;
    }

    public void disable(int i, int j) {
        String[] arr = listLabel[i][j].getName().split(";");

        list[Integer.parseInt(arr[0])][Integer.parseInt(arr[1])] = false;
        listLabel[Integer.parseInt(arr[0])][Integer.parseInt(arr[1])].setVisible(false);

        listLabel[i][j].setName(arr[0] + ";" + arr[1] + ";" + arr[2] + ";" + arr[3] + ";disable");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(690, 720));
        setPreferredSize(new java.awt.Dimension(690, 720));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(GamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GamePlay().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
