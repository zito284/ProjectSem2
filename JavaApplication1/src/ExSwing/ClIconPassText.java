package ExSwing;

/*
 * Copyright 2010 Georgios Migdos <cyberpython@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * under the License.
 */
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

/**
 * 
 * @authorGeorgios Migdos <cyberpython@gmail.com>
 */
@SuppressWarnings("serial")
public class ClIconPassText extends JPasswordField {

	private Icon icon;
	private Insets dummyInsets;

	public ClIconPassText() {
		super();
		this.icon = null;
                JPasswordField dummy = new JPasswordField();   
                //change this to work with LAF
	//	Border border = UIManager.getBorder("TextField.border");
		Border border = dummy.getBorder();                
		this.dummyInsets = border.getBorderInsets(dummy);
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Icon getIcon() {
		return this.icon;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int textX = 2;

		if (this.icon != null) {
			int iconWidth = icon.getIconWidth();
			int iconHeight = icon.getIconHeight();
			int x = dummyInsets.left + 5;// this is our icon's x
			textX = x + iconWidth + 2; // this is the x where text should start
			int y = (this.getHeight() - iconHeight) / 2;
			icon.paintIcon(this, g, x, y);
		}

		setMargin(new Insets(2, textX, 2, 2));

	}

}
