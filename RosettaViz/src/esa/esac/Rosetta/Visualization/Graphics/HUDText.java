package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

import esa.esac.Rosetta.Visualization.GlobalTools;

public class HUDText {
	BitmapFont font;
	BitmapText hudText;
	public HUDText(Node guiNode, ViewPort mainView)
	{
	
	    font = GlobalTools.assetManager.loadFont("Interface/Fonts/Default.fnt");
		hudText = new BitmapText(font, false);          
		hudText.setSize(font.getCharSet().getRenderedSize());      // font size
		hudText.setColor(ColorRGBA.White);                             // font color
		hudText.setText("You can write any string here");             // the text
		hudText.setLocalTranslation(10 ,mainView.getCamera().getHeight() - hudText.getLineHeight(), 0);
		guiNode.attachChild(hudText);
	}
	
	public void updateText(String text)
	{
		hudText.setText(text);
	}
}
