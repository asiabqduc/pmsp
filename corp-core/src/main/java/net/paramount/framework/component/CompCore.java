/**
 * 
 */
package net.paramount.framework.component;

import java.io.Serializable;

import javax.inject.Inject;

import net.paramount.framework.logging.LogService;

/**
 * @author bqduc
 *
 */
public abstract class CompCore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7003880176947562783L;

	@Inject
	protected LogService log;
}
