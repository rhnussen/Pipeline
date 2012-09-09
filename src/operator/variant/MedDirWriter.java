package operator.variant;

import java.io.PrintStream;

import buffer.variant.VariantRec;

public class MedDirWriter extends VariantPoolWriter {

	public final static String[] keys = new String[]{VariantRec.GENE_NAME,
		 VariantRec.NM_NUMBER,
		 VariantRec.CDOT,
		 VariantRec.PDOT,
		 VariantRec.DEPTH,
		 VariantRec.EXON_NUMBER,
		 VariantRec.VARIANT_TYPE, 
		 VariantRec.EXON_FUNCTION,
		 VariantRec.EFFECT_PREDICTION2,
		 VariantRec.POP_FREQUENCY,
		 VariantRec.AMR_FREQUENCY,
		 VariantRec.EXOMES_FREQ,
		 VariantRec.CG69_FREQUENCY,
		 VariantRec.RSNUM, 
		 VariantRec.OMIM_ID,
		 VariantRec.HGMD_INFO,
		 VariantRec.DBNSFP_MIMDISEASE,
		 VariantRec.SIFT_SCORE, 
		 VariantRec.POLYPHEN_SCORE, 
		 VariantRec.PHYLOP_SCORE, 
		 VariantRec.MT_SCORE,
		 VariantRec.GERP_SCORE,
		 VariantRec.LRT_SCORE,
		 VariantRec.SIPHY_SCORE,
		 VariantRec.DBNSFP_DISEASEDESC,
		 VariantRec.DBNSFP_FUNCTIONDESC};

	@Override
	public void writeHeader(PrintStream outputStream) {
		StringBuilder builder = new StringBuilder();
		builder.append(keys[0]);
		for(int i=1; i<keys.length; i++) {
			builder.append("\t " + keys[i]);
		}

		outputStream.println(builder.toString());
	}

	@Override
	public void writeVariant(VariantRec rec, PrintStream outputStream) {
		StringBuilder builder = new StringBuilder();
		builder.append( rec.getPropertyOrAnnotation(keys[0]).trim() );
		for(int i=1; i<keys.length; i++) {
			String val = rec.getPropertyOrAnnotation(keys[i]).trim();
			builder.append("\t" + val);
		}


		outputStream.println(builder.toString());
	}
}