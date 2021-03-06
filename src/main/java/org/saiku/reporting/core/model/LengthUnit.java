/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2009 Pentaho Corporation.  All rights reserved.
 */

package org.saiku.reporting.core.model;

import java.io.Serializable;
import java.io.ObjectStreamException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.pentaho.reporting.engine.classic.core.util.ObjectStreamResolveException;

@XmlRootElement(name="length-unit")
@XmlAccessorType(XmlAccessType.FIELD)
public final class LengthUnit implements Serializable
{
	public LengthUnit() {
		super();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final LengthUnit POINTS = new LengthUnit("pt", 1);
	public static final LengthUnit PERCENTAGE = new LengthUnit("%", -1);
	public static final LengthUnit PICA = new LengthUnit("pc", 12);
	public static final LengthUnit MM = new LengthUnit("mm", 72 / (2.54 * 10));
	public static final LengthUnit CM = new LengthUnit("cm", 72.0 / 2.54);
	public static final LengthUnit INCH = new LengthUnit("in", 72);

	@XmlElement(name="dots-per-unit")
	@JsonProperty("dotsPerUnit")
	private double dotsPerUnit;
	
	@XmlElement(name="name")
	@JsonProperty("name")
	private String name;

	private LengthUnit(final String name, final double dotsPerUnit)
	{
		this.name = name;
		this.dotsPerUnit = dotsPerUnit;
	}

	public String getName()
	{
		return name;
	}

	public double getDotsPerUnit()
	{
		return dotsPerUnit;
	}

	public double convertFromPoints(final double points)
	{
		return points / dotsPerUnit;
	}

	public double convertToPoints(final double unit)
	{
		return unit * dotsPerUnit;
	}

	public static LengthUnit[] values()
	{
		return new LengthUnit[]{INCH, CM, MM, PICA, PERCENTAGE, POINTS};
	}
	/**
	 * Replaces the automatically generated instance with one of the enumeration instances.
	 *
	 * @return the resolved element
	 *
	 * @throws ObjectStreamException if the element could not be resolved.
	 */
	private Object readResolve ()
	throws ObjectStreamException
	{
		if (this.dotsPerUnit == INCH.dotsPerUnit)
		{
			return INCH;
		}
		if (this.dotsPerUnit == CM.dotsPerUnit)
		{
			return CM;
		}
		if (this.dotsPerUnit == MM.dotsPerUnit)
		{
			return MM;
		}
		if (this.dotsPerUnit == PICA.dotsPerUnit)
		{
			return PICA;
		}
		if (this.dotsPerUnit == PERCENTAGE.dotsPerUnit)
		{
			return PERCENTAGE;
		}
		if (this.dotsPerUnit == POINTS.dotsPerUnit)
		{
			return POINTS;
		}
		// unknown element alignment...
		throw new ObjectStreamResolveException();
	}

}
