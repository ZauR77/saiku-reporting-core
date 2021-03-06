/**
 * 
 */
package org.saiku.reporting.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.reporting.engine.classic.core.AbstractReportDefinition;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportPreProcessor;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.SubReport;
import org.pentaho.reporting.engine.classic.core.function.ProcessingContext;
import org.pentaho.reporting.engine.classic.core.function.StructureFunction;
import org.pentaho.reporting.engine.classic.core.modules.parser.bundle.writer.BundleWriterException;
import org.pentaho.reporting.engine.classic.core.states.datarow.DefaultFlowController;
import org.pentaho.reporting.engine.classic.core.wizard.DefaultDataAttributeContext;
import org.pentaho.reporting.engine.classic.wizard.WizardOverrideFormattingFunction;
import org.saiku.reporting.core.builder.AbstractBuilder;
import org.saiku.reporting.core.builder.MergeFormatUtil;
import org.saiku.reporting.core.builder.PageFooterBuilder;
import org.saiku.reporting.core.builder.PageHeaderBuilder;
import org.saiku.reporting.core.builder.RelationalGroupBuilder;
import org.saiku.reporting.core.builder.ReportFooterBuilder;
import org.saiku.reporting.core.builder.ReportHeaderBuilder;
import org.saiku.reporting.core.builder.SimpleCrosstabBuilder;
import org.saiku.reporting.core.builder.TableBuilder;
import org.saiku.reporting.core.model.ReportSpecification;

/**
 * @author mg
 *
 */
public class SaikuReportPreProcessor implements ReportPreProcessor {

	private static final Log logger = LogFactory.getLog(SaikuReportPreProcessor.class);

	protected DefaultDataAttributeContext attributeContext;
	protected AbstractReportDefinition definition;
	protected DefaultFlowController flowController;
	protected ReportSpecification reportSpecification;

	public void setReportSpecification(ReportSpecification reportSpecification) {
		this.reportSpecification = reportSpecification;
	}

	public ReportSpecification getReportSpecification() {
		return reportSpecification;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MasterReport performPreDataProcessing(final MasterReport definition,
			final DefaultFlowController flowController){

		//do nothing
		return definition;
	}

	public MasterReport performPreProcessing(final MasterReport definition,
			final DefaultFlowController flowController)
					throws ReportProcessingException
					{

//		try {
//			this.reportSpecification = SaikuReportPreProcessorUtil.loadReportSpecification(definition, definition.getResourceManager(), "saiku-report-spec");
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ReportProcessingException("failed to load saiku-report-spec from srpt file", e);
//		}
		
		final StructureFunction[] functions = definition.getStructureFunctions();
		boolean hasOverrideFunction = false;
		for (int i = 0; i < functions.length; i++)
		{
			final StructureFunction function = functions[i];
			if (function instanceof WizardOverrideFormattingFunction)
			{
				hasOverrideFunction = true;
				break;
			}
		}
		if (!hasOverrideFunction)
		{
			definition.addStructureFunction(new WizardOverrideFormattingFunction());
		}

		final ProcessingContext reportContext = flowController.getReportContext();
		this.definition = definition;
		this.flowController = flowController;
		this.attributeContext = new DefaultDataAttributeContext(reportContext.getOutputProcessorMetaData(),
				reportContext.getResourceBundleFactory().getLocale());

		RelationalGroupBuilder relationalGroupBuilder = 
				new RelationalGroupBuilder(attributeContext, definition, flowController, reportSpecification);

		relationalGroupBuilder.build();

		if(SaikuReportPreProcessorUtil.isCrosstab(reportSpecification)){	
			SimpleCrosstabBuilder tableBuilder = new SimpleCrosstabBuilder(attributeContext, definition, flowController, reportSpecification);
			tableBuilder.build();				
		}
		else{
			TableBuilder tableBuilder = new TableBuilder(attributeContext, definition, flowController, reportSpecification);
			tableBuilder.build();
		}

		ReportHeaderBuilder headerBuilder = new ReportHeaderBuilder(attributeContext, definition, flowController, reportSpecification);
		headerBuilder.build();		

		AbstractBuilder footerBuilder = new ReportFooterBuilder(attributeContext, definition, flowController, reportSpecification);
		footerBuilder.build();			

		PageHeaderBuilder pageHeaderBuilder = new PageHeaderBuilder(attributeContext, definition, flowController, reportSpecification);
		pageHeaderBuilder.build();		

		PageFooterBuilder pageFooterBuilder = new PageFooterBuilder(attributeContext, definition, flowController, reportSpecification);
		pageFooterBuilder.build();	

		MergeFormatUtil.mergePageSetup(reportSpecification, definition);

		return definition;
		}

	public SubReport performPreProcessing(final SubReport definition,
			final DefaultFlowController flowController)
					throws ReportProcessingException
					{
		return null;
					}

	public ReportPreProcessor clone()
	{
		try
		{
			return (ReportPreProcessor) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new IllegalStateException();
		}
	}
	
	@Override
	public SubReport performPreDataProcessing(SubReport arg0,
			DefaultFlowController arg1) throws ReportProcessingException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
