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
package net.sf.jasperreports.crosstabs.base;

import java.io.Serializable;

import net.sf.jasperreports.crosstabs.JRCrosstabMeasure;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.engine.util.JRCloneUtils;

/**
 * Base read-only crosstab measure implementation.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id$
 */
public class JRBaseCrosstabMeasure implements JRCrosstabMeasure, Serializable
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected String name;
	protected String valueClassName;
	protected String valueClassRealName = null;
	protected Class valueClass;
	protected JRExpression expression;
	protected byte calculation = JRVariable.CALCULATION_COUNT;
	protected String incrementerFactoryClassName;
	protected String incrementerFactoryClassRealName;
	protected Class incrementerFactoryClass;
	protected byte percentageOfType = JRCrosstabMeasure.PERCENTAGE_TYPE_NONE;
	protected String percentageCalculatorClassName;
	protected String percentageCalculatorClassRealName;
	protected Class percentageCalculatorClass;
	protected JRVariable variable;

	protected JRBaseCrosstabMeasure()
	{
	}
	
	public JRBaseCrosstabMeasure(JRCrosstabMeasure measure, JRBaseObjectFactory factory)
	{
		factory.put(measure, this);
		
		this.name = measure.getName();
		this.valueClassName = measure.getValueClassName();
		this.expression = factory.getExpression(measure.getValueExpression());
		this.calculation = measure.getCalculation();
		this.incrementerFactoryClassName = measure.getIncrementerFactoryClassName();
		this.percentageOfType = measure.getPercentageOfType();		
		this.percentageCalculatorClassName = measure.getPercentageCalculatorClassName();
		this.variable = factory.getVariable(measure.getVariable());
	}
	
	public String getName()
	{
		return name;
	}

	public String getValueClassName()
	{
		return valueClassName;
	}

	public JRExpression getValueExpression()
	{
		return expression;
	}

	public byte getCalculation()
	{
		return calculation;
	}

	public String getIncrementerFactoryClassName()
	{
		return incrementerFactoryClassName;
	}

	public byte getPercentageOfType()
	{
		return percentageOfType;
	}

	public Class getIncrementerFactoryClass()
	{
		if (incrementerFactoryClass == null)
		{
			String className = getIncrementerFactoryClassRealName();
			if (className != null)
			{
				try
				{
					incrementerFactoryClass = JRClassLoader.loadClassForName(className);
				}
				catch (ClassNotFoundException e)
				{
					throw new JRRuntimeException("Could not load measure incrementer class", e);
				}
			}
		}
		
		return incrementerFactoryClass;
	}

	/**
	 *
	 */
	private String getIncrementerFactoryClassRealName()
	{
		if (incrementerFactoryClassRealName == null)
		{
			incrementerFactoryClassRealName = JRClassLoader.getClassRealName(incrementerFactoryClassName);
		}
		
		return incrementerFactoryClassRealName;
	}

	public Class getValueClass()
	{
		if (valueClass == null)
		{
			String className = getValueClassRealName();
			if (className != null)
			{
				try
				{
					valueClass = JRClassLoader.loadClassForName(className);
				}
				catch (ClassNotFoundException e)
				{
					throw new JRRuntimeException("Could not load bucket value class", e);
				}
			}
		}
		
		return valueClass;
	}

	/**
	 *
	 */
	private String getValueClassRealName()
	{
		if (valueClassRealName == null)
		{
			valueClassRealName = JRClassLoader.getClassRealName(valueClassName);
		}
		
		return valueClassRealName;
	}

	public JRVariable getVariable()
	{
		return variable;
	}

	public String getPercentageCalculatorClassName()
	{
		return percentageCalculatorClassName;
	}

	public Class getPercentageCalculatorClass()
	{
		if (percentageCalculatorClass == null)
		{
			String className = getPercentageCalculatorClassRealName();
			if (className != null)
			{
				try
				{
					percentageCalculatorClass = JRClassLoader.loadClassForName(className);
				}
				catch (ClassNotFoundException e)
				{
					throw new JRRuntimeException("Could not load measure percentage calculator class", e);
				}
			}
		}
		
		return percentageCalculatorClass;
	}

	/**
	 *
	 */
	private String getPercentageCalculatorClassRealName()
	{
		if (percentageCalculatorClassRealName == null)
		{
			percentageCalculatorClassRealName = JRClassLoader.getClassRealName(percentageCalculatorClassName);
		}
		
		return percentageCalculatorClassRealName;
	}

	/**
	 * 
	 */
	public Object clone() 
	{
		try
		{
			JRBaseCrosstabMeasure clone = (JRBaseCrosstabMeasure) super.clone();
			clone.expression = (JRExpression) JRCloneUtils.nullSafeClone(expression);
			clone.variable = (JRVariable) JRCloneUtils.nullSafeClone(variable);
			return clone;
		}
		catch (CloneNotSupportedException e)
		{
			// never
			throw new JRRuntimeException(e);
		}
	}
}
