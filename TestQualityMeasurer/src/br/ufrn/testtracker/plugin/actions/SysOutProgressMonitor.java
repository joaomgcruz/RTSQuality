package br.ufrn.testtracker.plugin.actions;

import java.io.PrintStream;
import java.text.DecimalFormat;

import org.eclipse.core.runtime.IProgressMonitor;

public class SysOutProgressMonitor implements IProgressMonitor
{
	private static DecimalFormat format = new DecimalFormat("##0.00");
	private boolean cancelled = false;
	private double max;
	private double internal;
	private String taskName = "";
	private String subTaskName = "";
	public static PrintStream out = System.out;
	
	private void printProgress(boolean newline)
	{
		if (newline)
		{
			//System.out.println();
		}
		else
		{
			//System.out.print("\r                                                                              \r");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("PROGRESS ");
		double percent = (internal/max) * 100.0;
		if (Double.isNaN(percent))
		{
			percent = 100;
		}
		if (percent < 10)
		{
			sb.append("  ");
		}
		else if (percent < 99.99)
		{
			sb.append(" ");
		}
		sb.append(format.format(percent));
		sb.append("%: " + taskName + " " + subTaskName);

		//if (internal == max)
		//{
			out.println(sb);
		//}
	}

	public void beginTask(String arg0, int arg1)
	{
		taskName = arg0;
		subTaskName = "";
		max = arg1;
		internal = 0;
		printProgress(true);
	}

	public void done()
	{
		max = internal;
		printProgress(false);
		out.println();
		taskName = subTaskName = "";
	}

	public void internalWorked(double arg0)
	{
		internal += arg0;
		printProgress(false);
	}

	public boolean isCanceled()
	{
		return cancelled;
	}

	public void setCanceled(boolean arg0)
	{
		cancelled = arg0;
	}

	public void setTaskName(String arg0)
	{
		taskName = arg0;
		printProgress(true);
	}

	public void subTask(String arg0)
	{
		subTaskName = arg0;
		printProgress(true);
	}

	public void worked(int arg0)
	{
		internal = arg0;
		printProgress(false);
	}
	
}