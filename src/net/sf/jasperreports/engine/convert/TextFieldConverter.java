/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Contributors:
 * Eugene D - eugenedruy@users.sourceforge.net 
 * Adrian Jackson - iapetus@users.sourceforge.net
 * David Taylor - exodussystems@users.sourceforge.net
 * Lars Kristensen - llk@users.sourceforge.net
 */
package net.sf.jasperreports.engine.convert;

import net.sf.jasperreports.engine.JRCommonText;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.base.JRBasePrintText;
import net.sf.jasperreports.engine.util.JRExpressionUtil;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id$
 */
public class TextFieldConverter extends TextElementConverter
{

	/**
	 *
	 */
	private final static TextFieldConverter INSTANCE = new TextFieldConverter();
	
	/**
	 *
	 */
	private TextFieldConverter()
	{
	}

	/**
	 *
	 */
	public static TextFieldConverter getInstance()
	{
		return INSTANCE;
	}
	
	/**
	 *
	 */
	public JRPrintElement convert(ReportConverter reportConverter, JRElement element)
	{
		JRBasePrintText printText = new JRBasePrintText(reportConverter.getDefaultStyleProvider());
		JRTextField textField = (JRTextField)element;
		
		copyTextElement(reportConverter, textField, printText);
		
		printText.setMarkup(JRCommonText.MARKUP_NONE);//text fields are never converted to styled print text elements
		printText.setAnchorName(JRExpressionUtil.getExpressionText(textField.getAnchorNameExpression()));
		printText.setBookmarkLevel(textField.getBookmarkLevel());
		printText.setLinkType(textField.getLinkType());
		printText.setPattern(textField.getOwnPattern());
		
		measureTextElement(printText, JRExpressionUtil.getExpressionText(textField.getExpression()));

		return printText;
	}

}
