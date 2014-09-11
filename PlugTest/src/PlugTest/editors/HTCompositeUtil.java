package PlugTest.editors;

import org.eclipse.swt.graphics.Rectangle;

public class HTCompositeUtil {
	
	
	public Rectangle getRectangle(int index,int horizontalMargin,int verticalMargin,int width,int height,int verticalSpace){
		Rectangle rectangle=new Rectangle(horizontalMargin,verticalMargin+((index)*(height+verticalSpace)), (width-horizontalMargin), height);
		return rectangle;
	}
}
