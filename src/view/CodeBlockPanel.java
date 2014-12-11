package view;

import control.Controller;
import model.CodeBlock;
import util.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class CodeBlockPanel extends JButton {

<<<<<<< HEAD
	private boolean conditional = false;
	private boolean endButton = false;
	private boolean elseButton = false;
	private boolean macroButton = false;
	private Controller controller;
	private int id;

	private PlayPanel playPanel;
	private boolean editMode = false;


	public CodeBlockPanel(String buttonText) {
		setText(buttonText);
		setFocusable(false);
		setFont(Constants.FONT_OPEN_SANS_14);
		setForeground(Color.WHITE);

		setPreferredSize(new Dimension(116, 41));
		setBorderPainted(false);
		if (!buttonText.equals("")) {
			setBackground(Constants.COLOR_SMOOTH_GREEN);
			setOpaque(true);
		} else {
			setOpaque(false);
		}

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				setBackground(Constants.COLOR_SMOOTH_GREEN);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Constants.COLOR_ACTIONS);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Constants.COLOR_SMOOTH_GREEN);
			}
		});
		setBorder(new EmptyBorder(2, 2, 2, 2));
	}

	public CodeBlockPanel(String buttonText, int id, Controller controller, final PlayPanel playPanel) {
		this.controller = controller;
		this.playPanel = playPanel;
		this.id = id;
		this.conditional = buttonText.equals("IF") || buttonText.equals("WHILE");
		this.endButton = buttonText.equals("END");
		this.elseButton = buttonText.equals("ELSE");

		for (final String name : controller.userController.getMacros())
		{
			if(name.equals(buttonText))
				macroButton = true;
		}

		setText(buttonText);
		setFocusable(false);
		setFont(Constants.FONT_OPEN_SANS_14);
		setForeground(Color.WHITE);

		setPreferredSize(new Dimension(110, 30));
		if(conditional || endButton || elseButton)
			setBorder(new MatteBorder(2,2,2,2, Constants.COLOR_CONDITIONALS));
		else if(macroButton)
			setBorder(new MatteBorder(2,2,2,2, Constants.COLOR_MACRO));
		else
			setBorder(new MatteBorder(2, 2, 2, 2, Constants.COLOR_ACTIONS));
		setBackground(Color.GRAY);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					CodeBlockPanel.this.controller.codeController.removeBlock(CodeBlockPanel.this.id);
					playPanel.codePanel.refreshPanel();
					playPanel.actionPanel.editMode = false;
					playPanel.actionPanel.repaintActionPanel();
				} else {
					if (!conditional && !endButton && !elseButton)
					{
						setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
						playPanel.actionPanel.updateActionPanel(CodeBlockPanel.this);
						editMode = true;
						playPanel.codePanel.updateBlockForEdit(CodeBlockPanel.this);
					}
					else
					{
						playPanel.actionPanel.editMode = false;
						playPanel.actionPanel.repaintActionPanel();
						playPanel.codePanel.updateBlockForEdit(null);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (!editMode)
					setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ?
							Constants.COLOR_CONDITIONALS_HOVER : Constants.COLOR_ACTIONS_HOVER));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!editMode)
					if(conditional || endButton || elseButton)
						setBorder(new MatteBorder(2,2,2,2, Constants.COLOR_CONDITIONALS));
					else if(macroButton)
						setBorder(new MatteBorder(2,2,2,2, Constants.COLOR_MACRO));
					else
						setBorder(new MatteBorder(2, 2, 2, 2, Constants.COLOR_ACTIONS));
			}
		});
	}

	public boolean isConditional() {
		return conditional;
	}

	public boolean isEndButton() {
		return endButton;
	}

	public boolean isElseButton() {
		return elseButton;
	}

	public boolean isMacroButton() {
		return macroButton;
	}

	public int getId() {
		return this.id;
	}

	public void exitEditMode()
	{
		if(conditional || endButton || elseButton)
			setBorder(new MatteBorder(2,2,2,2, Constants.COLOR_CONDITIONALS));
		else if(macroButton)
			setBorder(new MatteBorder(2,2,2,2, Constants.COLOR_MACRO));
		else
			setBorder(new MatteBorder(2, 2, 2, 2, Constants.COLOR_ACTIONS));
		this.editMode = false;
	}

	public void updateBorderColor(Color color)
	{
		if (color == null)
		{
			if(conditional || endButton || elseButton)
				setBorder(new MatteBorder(2,2,2,2, Constants.COLOR_CONDITIONALS));
			else if(macroButton)
				setBorder(new MatteBorder(2,2,2,2, Constants.COLOR_MACRO));
			else
				setBorder(new MatteBorder(2, 2, 2, 2, Constants.COLOR_ACTIONS));
		}
		else
		{
			setBorder(new MatteBorder(2, 2, 2, 2, color));
		}
	}
=======
    private boolean conditional = false;
    private boolean endButton = false;
    private boolean elseButton = false;
    private boolean declarationButton = false;
    private Controller controller;
    private int id;
    private PlayPanel playPanel;
    private boolean editMode = false;
    private boolean insertMode = false;


    public CodeBlockPanel(String buttonText) {
        setText(buttonText);
        setFocusable(false);
        setFont(Constants.FONT_OPEN_SANS_14);
        setForeground(Color.WHITE);

        setPreferredSize(new Dimension(116, 41));
        setBorderPainted(false);
        if (!buttonText.equals("")) {
            setBackground(Constants.COLOR_SMOOTH_GREEN);
            setOpaque(true);
        } else {
            setOpaque(false);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setBackground(Constants.COLOR_SMOOTH_GREEN);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Constants.COLOR_ACTIONS);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Constants.COLOR_SMOOTH_GREEN);
            }
        });
        setBorder(new EmptyBorder(2, 2, 2, 2));
    }

	public CodeBlockPanel(String buttonText, int id, Controller controller, final PlayPanel playPanel) {
        this.controller = controller;
        this.playPanel = playPanel;
        this.id = id;
        this.conditional = buttonText.equals("IF") || buttonText.equals("WHILE");
        this.endButton = buttonText.equals("END");
        this.elseButton = buttonText.equals("ELSE");
        this.declarationButton = buttonText.contains("FACING") || buttonText.contains("DIR");
        setText(buttonText);
        setFocusable(false);
        setFont(Constants.FONT_OPEN_SANS_14);
        setForeground(Color.WHITE);

        setPreferredSize(new Dimension(110, 30));
        setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ?
                Constants.COLOR_CONDITIONALS : Constants.COLOR_ACTIONS));
        setBackground(Color.GRAY);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    CodeBlockPanel.this.controller.codeController.removeBlock(CodeBlockPanel.this.id);
                    playPanel.codePanel.refreshPanel();
                    playPanel.actionPanel.editMode = false;
                    playPanel.actionPanel.insertMode = false;
                    playPanel.actionPanel.repaintActionPanel();
                } else if(SwingUtilities.isLeftMouseButton(e)){
                    if (!conditional && !endButton && !elseButton)
                    {
                        setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
                        editMode = true;
                        insertMode = false;
                        playPanel.actionPanel.updateActionPanel(CodeBlockPanel.this);
                        playPanel.codePanel.updateBlockForEdit(CodeBlockPanel.this);
                    }
                    else
                    {
                        playPanel.actionPanel.editMode = false;
                        playPanel.actionPanel.repaintActionPanel();
                        playPanel.codePanel.updateBlockForEdit(null);
                    }
                } else if(SwingUtilities.isRightMouseButton(e)){
                    if (!conditional && !endButton && !elseButton && !declarationButton){
                        setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
                        editMode = false;
                        insertMode = true;
                        playPanel.actionPanel.updateActionPanel(CodeBlockPanel.this);
                        playPanel.codePanel.updateBlockForInsert(CodeBlockPanel.this);
                    }else{
                        playPanel.actionPanel.editMode = false;
                        playPanel.actionPanel.insertMode = false;
                        playPanel.actionPanel.repaintActionPanel();
                        playPanel.codePanel.updateBlockForInsert(null);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!editMode && !insertMode)
                    setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ?
                            Constants.COLOR_CONDITIONALS_HOVER : Constants.COLOR_ACTIONS_HOVER));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!editMode && !insertMode)
                    setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ?
                            Constants.COLOR_CONDITIONALS : Constants.COLOR_ACTIONS));
            }
        });
    }

    public boolean isConditional() {
        return conditional;
    }

    public boolean isEndButton() {
        return endButton;
    }

    public boolean isElseButton() {
        return elseButton;
    }
    
    public boolean isInsertMode() {
        return insertMode;
    }
        
    public boolean isEditMode() {
        return editMode;
    }

    public int getId() {
        return this.id;
    }

    public void exitEditMode()
    {
        setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ?
                Constants.COLOR_CONDITIONALS : Constants.COLOR_ACTIONS));
        this.editMode = false;
    }

    public void exitInsertMode(){
        setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ? 
                Constants.COLOR_CONDITIONALS : Constants.COLOR_ACTIONS));
	this.insertMode = false;
    }

    public void updateBorderColor(Color color)
    {
        if (color == null)
        {
            setBorder(new MatteBorder(2, 2, 2, 2, conditional || endButton || elseButton ?
                    Constants.COLOR_CONDITIONALS : Constants.COLOR_ACTIONS));
        }
        else
        {
            setBorder(new MatteBorder(2, 2, 2, 2, color));
        }
    }
>>>>>>> db25332c4d7302ae696b9d8b28d9fed1196ecf5f

}
