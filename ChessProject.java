import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/*
	This class can be used as a starting point for creating your Chess game project. The only piece that
	has been coded is a white pawn...a lot done, more to do!
*/

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {
    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;
	static int startX;
	int startY;
	int initialX;
	int initialY;
	static int newX;
	static int newY;
	int spacesmoved;
	static int singlenum;
	JPanel panels;
	JLabel pieces;



    public ChessProject(){
        Dimension boardSize = new Dimension(600, 600);

        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            chessBoard.add( square );

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
            else
                square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
        }

        // Setting up the Initial Chess board.
		for(int i=8;i < 16; i++){
       		pieces = new JLabel( new ImageIcon("WhitePawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
	        panels.add(pieces);
		}
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(0);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(1);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(6);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishup.png") );
		panels = (JPanel)chessBoard.getComponent(2);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishup.png") );
		panels = (JPanel)chessBoard.getComponent(5);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKing.png") );
		panels = (JPanel)chessBoard.getComponent(3);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
		panels = (JPanel)chessBoard.getComponent(4);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(7);
	    panels.add(pieces);
		for(int i=48;i < 56; i++){
       		pieces = new JLabel( new ImageIcon("BlackPawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
	        panels.add(pieces);
		}
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(56);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(57);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(62);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishup.png") );
		panels = (JPanel)chessBoard.getComponent(58);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishup.png") );
		panels = (JPanel)chessBoard.getComponent(61);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKing.png") );
		panels = (JPanel)chessBoard.getComponent(59);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackQueen.png") );
		panels = (JPanel)chessBoard.getComponent(60);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(63);
	    panels.add(pieces);
    }

	/*
		This method checks if there is a piece present on a particular square.
	*/
	private Boolean piecePresent(int x, int y){
		Component c = chessBoard.findComponentAt(x, y);
		if(c instanceof JPanel){
			return false;
		}
		else{
			return true;
		}
	}

	/*
		This is a method to check if a piece is a Black piece.
	*/
	private Boolean checkWhiteOponent(int newX, int newY){
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel)c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if(((tmp1.contains("Black")))){
			oponent = true;
		}
		else{
			oponent = false;
		}
		return oponent;
	}

	/*
		This method is called when we press the Mouse. So we need to find out what piece we have
		selected. We may also not have selected a piece!
	*/
    public void mousePressed(MouseEvent e){
        chessPiece = null;
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel)
			return;

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel)c;
		initialX = e.getX();
		initialY = e.getY();
		startX = (e.getX()/75);
		startY = (e.getY()/75);
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }

    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) return;
         chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
     }

 	/*
		This method is used when the Mouse is released...we need to make sure the move was valid before
		putting the piece back on the board.
	*/
    public void mouseReleased(MouseEvent e) {
		if (chessPiece == null) return;

		chessPiece.setVisible(false);
		Boolean success = false;
		Component c = chessBoard.findComponentAt(e.getX(), e.getY());
		String tmp = chessPiece.getIcon().toString();
		String pieceName = tmp.substring(0, (tmp.length() - 4));
		Boolean validMove = false;
		singlenum =(newX + newY) - (startX + startY);


		/*
			The only piece that has been enabled to move is a White Pawn...but we should really have this is a separate
			method somewhere...how would this work.

			So a Pawn is able to move two squares forward one its first go but only one square after that.
			The Pawn is the only piece that cannot move backwards in chess...so be careful when committing
			a pawn forward. A Pawn is able to take any of the opponent’s pieces but they have to be one
			square forward and one square over, i.e. in a diagonal direction from the Pawns original position.
			If a Pawn makes it to the top of the other side, the Pawn can turn into any other piece, for
			demonstration purposes the Pawn here turns into a Queen.
		*/
		if (pieceName.equals("White")) {
			if (startY == 1) {
				validMove = true;
			} else {//gets postition of the where we want to move
				newY = e.getY() / 75;
				newX = e.getX() / 75;

				spacesmoved = startX - newX;
				if (spacesmoved < 0) {
					spacesmoved = spacesmoved - 1;
				}
				{
					for (int i = 1; i <= spacesmoved; i++) {
						if ((singlenum % 11 == 0) && (newY > startY)) {
							if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
								validMove = true;
								break;
							} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
								validMove = true;
								break;
							} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
								validMove = true;
								break;
							}
							validMove = false;
							break;
						} else {
							validMove = true;
						}
						if ((spacesmoved % -11 == 0) && (newY < startY)) {
							if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
								if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
									validMove = true;
									break;
								} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
									validMove = true;
									break;
								}
								validMove = false;
								break;
							} else {
								validMove = true;

								if ((spacesmoved % -9 == 0) && (newY > startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									}
									validMove = true;
								}
								if ((spacesmoved % -9 == 0) && (newY > startY)) {
									if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
										if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									}
									validMove = true;
								}
								//up right
								if ((spacesmoved % 9 == 0) && (newY < startY)) {
									if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;
									}
								} else {
									validMove = false;
									break;
								}

							}
						}
					}
				}

				if (pieceName.equals("Black")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}



				/*
				if (pieceName.equals("WhiteBishup")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}

				if (pieceName.equals("WhiteKing")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}
				if (pieceName.equals("WhiteKnight")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}

				if (pieceName.equals("WhiteQueen")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}

				if (pieceName.equals("WhiteRook")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}

				/*
				 *
				 * this is for the black pawn movements
				 * will also add in the ability to move diagonally.
				 */
				/*
				if (pieceName.equals("BlackPawn")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}

				if (pieceName.equals("BlackBishup")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}

				if (pieceName.equals("BlackKing")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}
				if (pieceName.equals("BlackKnight")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}
				if (pieceName.equals("BlackQueen")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}

				if (pieceName.equals("BlackRook")) {
					if (startY == 1) {
						validMove = true;
					} else {//gets postition of the where we want to move
						newY = e.getY() / 75;
						newX = e.getX() / 75;

						spacesmoved = startX - newX;
						if (spacesmoved < 0) {
							spacesmoved = spacesmoved - 1;
						}
						{
							for (int i = 1; i <= spacesmoved; i++) {
								if ((singlenum % 11 == 0) && (newY > startY)) {
									if (piecePresent((startX + i) * 75, (startY + i) * 75)) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
										validMove = true;
										break;
									} else if ((i == spacesmoved) && (checkWhiteOponent((startX + i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
										validMove = true;
										break;
									}
									validMove = false;
									break;
								} else {
									validMove = true;
								}
								if ((spacesmoved % -11 == 0) && (newY < startY)) {
									if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
										if ((i == singlenum) && checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) {
											validMove = true;
											break;
										} else if ((i == singlenum) && (!checkWhiteOponent((startX - i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
											validMove = true;
											break;
										}
										validMove = false;
										break;
									} else {
										validMove = true;

										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("White"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										if ((spacesmoved % -9 == 0) && (newY > startY)) {
											if (piecePresent((startX - i) * 75, (startY + i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX - i) * 75, (startY + i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											}
											validMove = true;
										}
										//up right
										if ((spacesmoved % 9 == 0) && (newY < startY)) {
											if (piecePresent((startX + i) * 75, (startY - i) * 75)) {
												if ((i == singlenum) && (checkWhiteOponent((startX + i) * 75, (startY - i) * 75)) && (pieceName.contains("Black"))) {
													validMove = true;
													break;
												}
												validMove = false;
												break;
											} else {
												validMove = true;
											}
										} else {
											validMove = false;
											break;
										}

									}
								}
							}
						}
					}
				}

				if (!validMove) {
					int location = 0;
					if (startY == 0) {
						location = startX;
					} else {
						location = (startY * 8) + startX;
					}
					String pieceLocation = pieceName + ".png";
					pieces = new JLabel(new ImageIcon(pieceLocation));
					panels = (JPanel) chessBoard.getComponent(location);
					panels.add(pieces);
				} else {
					if (success) {
						int location = 56 + (e.getX() / 75);
						if (c instanceof JLabel) {
							Container parent = c.getParent();
							parent.remove(0);
							pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
							parent = (JPanel) chessBoard.getComponent(location);
							parent.add(pieces);
						} else {
							Container parent = (Container) c;
							pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
							parent = (JPanel) chessBoard.getComponent(location);
							parent.add(pieces);
						}
					} else {
						if (c instanceof JLabel) {
							Container parent = c.getParent();
							parent.remove(0);
							parent.add(chessPiece);
						} else {
							Container parent = (Container) c;
							parent.add(chessPiece);
						}
						chessPiece.setVisible(true);
					}

				}
			}
		}
	}
	 */

    public void mouseClicked(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
	}
    public void mouseEntered(MouseEvent e){
    }
    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new ChessProject();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
		new AIImp();
		 }//end main method
}//end class




