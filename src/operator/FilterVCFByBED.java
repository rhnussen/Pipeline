package operator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import pipeline.Pipeline;

import buffer.BEDFile;
import buffer.FileBuffer;
import buffer.VCFFile;

/**
 * Filters a vcf file and removes all sites not in the given BED file, writing the results to a new file
 * @author brendan
 *
 */
public class FilterVCFByBED extends IOOperator {


	@Override
	public void performOperation() throws OperationFailedException {
		BEDFile bedFile = (BEDFile) getInputBufferForClass(BEDFile.class);		
		VCFFile inVCF = (VCFFile) getInputBufferForClass(VCFFile.class);
		VCFFile outVCF = (VCFFile) getOutputBufferForClass(VCFFile.class);
		
	//	Logger logger = Logger.getLogger(Pipeline.primaryLoggerName);
		
		if (! bedFile.isMapCreated())
			try {
				bedFile.buildIntervalsMap();
			} catch (IOException e) {
				throw new OperationFailedException("Could not create intervals map for bed file: " + bedFile.getAbsolutePath() + "\n" + e.getMessage(), this);
				
			}
		

		try {
			//Write header of old vcf to new vcf file
			BufferedReader reader = new BufferedReader(new FileReader(inVCF.getFile()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(outVCF.getFile()));
			String line = reader.readLine();
			while (line != null && line.startsWith("#")) {
				writer.write(line + "\n");
				line = reader.readLine();
			}

			VCFLineParser vParser = new VCFLineParser(inVCF.getFile());
			int varsFound = 0;
			while( vParser.advanceLine()) {
				String contig = vParser.getContig();
				int pos = vParser.getPosition();
				//System.out.println("Searching for contig: " + contig + " pos: " + pos);
				if (bedFile.contains(contig, pos)) {
					varsFound++;
					//System.out.println("found contig: " + contig + " pos:" + pos + ", have " + varsFound + " total");
					writer.write(vParser.getCurrentLine() + "\n");
				}
				else {
					//System.out.println("didnt find it :{");
				}
			}
			writer.close();
		} catch (IOException e) {
			throw new OperationFailedException("Could not read / write vcf file for filtration\n" + e.getMessage(), this);
		}
		
		
		
	}

}