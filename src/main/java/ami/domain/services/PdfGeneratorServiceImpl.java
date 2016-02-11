package ami.domain.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import ami.domain.model.amicase.Amendment;
import ami.infrastructure.database.model.AmiRequestView;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService{
	
	private static final DateTimeFormatter dateFormatter =  DateTimeFormat.forPattern("MM/dd/yy HH:mm");
	private static final PDFont BOLD_FONT = PDType1Font.HELVETICA_BOLD;
	private static final PDFont NORMAL_FONT = PDType1Font.HELVETICA;
	private static final int NORMAL_SIZE = 8;
	
	private static final int FIRST_COL   = 20;
	private static final int SECOND_COL  = FIRST_COL  + 200;
	private static final int THIRD_COL   = SECOND_COL + 210;

	@Override
	public void generatePdf(AmiRequestView amiRequestView) throws IOException{
		
//		SimpleDateFormat dateParser = new SimpleDateFormat("MM/dd/yy HH:mm");
		
		
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage( page );
	
		// Create a new font object selecting one of the PDF base fonts
		
	
		// Start a new content stream which will "hold" the to be created content
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
	
		String caseNumber   = amiRequestView.getCaseNumber();
		
		// 1st row
		String hospitalName = amiRequestView.getHospitalName();
		String animalName 	= amiRequestView.getAmiRequest().getPatientInfo().getAnimalName();
		DateTime requestedDate 	= amiRequestView.getHasBeenSavedAndSubmittedToRadiologist();
		String requestedDateFormatted = dateFormatter.print(requestedDate);
		
		// 2nd row
		String veterinarian = amiRequestView.getAmiRequest().getHospitalAndClientInfo().getVet();
		String firstName 	= amiRequestView.getAmiRequest().getHospitalAndClientInfo().getClientFirstName() ;
		String lastName 	= amiRequestView.getAmiRequest().getHospitalAndClientInfo().getClientLastName();
		String clientName = firstName + " "+ lastName;
		DateTime closedDate 	= amiRequestView.getCaseClosed();
		String closedDateFormatted = dateFormatter.print(closedDate);//dateParser.format(closedDate);
		
		// 3rd row
		String requester 	= amiRequestView.getUserName();
		String clientId 	= amiRequestView.getAmiRequest().getHospitalAndClientInfo().getClientId();
		
		
		// pet info
		String breed 		= amiRequestView.getAmiRequest().getPatientInfo().getBreeds();
		String species 		= amiRequestView.getAmiRequest().getPatientInfo().getSpecies();
		String age 			= amiRequestView.getAmiRequest().getPatientInfo().getAgeLabel();
		String sex 			= amiRequestView.getAmiRequest().getPatientInfo().getAnimalSex();
		String weight 		= amiRequestView.getAmiRequest().getPatientInfo().getAnimalWeight();
		String weightUom	= amiRequestView.getAmiRequest().getPatientInfo().getAnimalWeightUom();
		String weightValue = weight +" "+ weightUom;
		
		String exam							= amiRequestView.getAmiRequest().getVetObservation().getExam();
		String diagnosis 					= amiRequestView.getAmiRequest().getVetObservation().getTentativeDiagnosis();
		String radiographicImpression 		= amiRequestView.getRadiographicImpression();
		String radiographicInterpretation 	= amiRequestView.getRadiographicInterpretation();
		String recommendation 				= amiRequestView.getRecommendation();
		List<Amendment> amendments = amiRequestView.getAmendments();
		
		
		int firstRow   = 600;
		int secondRow  = firstRow - 10;
		int thirdRow   = secondRow - 10;
		int petInfodRow   = thirdRow - 50;
		int examRow   	  = petInfodRow - 50;
		int tentativeDiagnosisRow   = examRow - 50;
		int radioInterpretationRow  = tentativeDiagnosisRow - 50;
		int radioImpressionRow   	= radioInterpretationRow - 50;
		int recommendationRow   	= radioImpressionRow - 50;
		int amendmentRow   			= recommendationRow - 50;
		
		
		
		// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, 18 );
			contentStream.newLineAtOffset( 160, 700 );
			contentStream.showText( "Animal Medical Imaging Report" );
		contentStream.endText();
		
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, 14 );
			contentStream.newLineAtOffset( FIRST_COL, firstRow + 25 );
			contentStream.showText( "Case#" + caseNumber   );
		contentStream.endText();
		
		//**************  1st Row  *****************
		//  hospitalName ,animalName ,requestedDate
		contentStream.beginText();
			contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( FIRST_COL, firstRow );
			contentStream.showText( "Hospital Name" );
		contentStream.endText();
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( FIRST_COL+ 55, firstRow  );
			contentStream.showText( hospitalName );
		contentStream.endText();
		
	
		contentStream.beginText();
			contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( SECOND_COL, firstRow );
			contentStream.showText( "Animal Name"  );
		contentStream.endText();
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( SECOND_COL+50, firstRow );
			contentStream.showText( animalName );
		contentStream.endText();
		
		
		contentStream.beginText();
			contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( THIRD_COL, firstRow );
			contentStream.showText( "Requested Date"  );
		contentStream.endText();
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( THIRD_COL+60, firstRow );
			contentStream.showText( requestedDateFormatted );
		contentStream.endText();
		
		//**************  2nd Row  *****************
		// veterinarian, clientName, closedDate
		contentStream.beginText();
			contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( FIRST_COL, secondRow );
			contentStream.showText( "Veterinarian" );
		contentStream.endText();
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( FIRST_COL + 55, secondRow );
			contentStream.showText( veterinarian );
		contentStream.endText();
		
	
		contentStream.beginText();
			contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( SECOND_COL, secondRow );
			contentStream.showText( "Client Name"  );
		contentStream.endText();
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( SECOND_COL +50, secondRow );
			contentStream.showText(  clientName );
		contentStream.endText();
		
		contentStream.beginText();
			contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( THIRD_COL, secondRow );
			contentStream.showText( "Closed Date" );
		contentStream.endText();
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( THIRD_COL +60, secondRow );
			contentStream.showText(  closedDateFormatted );
		contentStream.endText();
		
		//**************  3rd Row  *****************
		//requester , clientId
		contentStream.beginText();
			contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( FIRST_COL, thirdRow );
			contentStream.showText( "Requester" );
		contentStream.endText();
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( FIRST_COL + 55, thirdRow );
			contentStream.showText(  requester );
		contentStream.endText();
	
		contentStream.beginText();
			contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( SECOND_COL, thirdRow );
			contentStream.showText( "Client Id");
		contentStream.endText();
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( SECOND_COL+50, thirdRow );
			contentStream.showText(clientId );
		contentStream.endText();
		


//radioInterpretationR
//radioImpressionRow  
//recommendationRow   
//amendmentRow   				
//		breed , species, age , sex , weightValue
		
		String petInfo = breed+ ", "+ species +", "+ age+", "+ sex+", "+ weightValue;
		
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( FIRST_COL, petInfodRow );
			contentStream.showText( "Pet Information" );
		contentStream.endText();
		contentStream.beginText();
			contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( FIRST_COL, petInfodRow - 10);
			contentStream.showText(  petInfo );
		contentStream.endText();
		
		
		// now the row is relative..
		int currentRowPosition = examRow;
		
		currentRowPosition = doExam(contentStream,  "Exam" ,exam,  currentRowPosition);
		
		currentRowPosition = currentRowPosition -20;
		currentRowPosition = doExam(contentStream,  "Tentative Diagnosis" ,diagnosis, currentRowPosition);
		
		if( amiRequestView.getContract().toUpperCase().startsWith("NOT")){
			
			currentRowPosition = currentRowPosition -20;
			currentRowPosition = doExam(contentStream,  "Radiographic Interpretation" ,radiographicInterpretation, currentRowPosition);
		}
		
		currentRowPosition = currentRowPosition -20;
		currentRowPosition = doExam(contentStream,  "Radiographic Impression" ,radiographicImpression, currentRowPosition);
		
		currentRowPosition = currentRowPosition -20;
		currentRowPosition = doExam(contentStream,  "Recommendation" ,recommendation, currentRowPosition);
		
		currentRowPosition = currentRowPosition -20;
		currentRowPosition = doAmendments(contentStream,  "Amendments" ,amendments, currentRowPosition);
		
		
		// tentative diagnosis
//		currentRowPosition = currentRowPosition -20;
//		
//		contentStream.beginText();
//			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
//			contentStream.newLineAtOffset( FIRST_COL, currentRowPosition );
//			contentStream.showText( "Tentative Diagnosis" );
//		contentStream.endText();
//	
//		String tentativeDiagnosisLines[] = diagnosis.split("\\r?\\n");
//		for (int i = 0; i < tentativeDiagnosisLines.length; i++) {
//			
//			String aLine = tentativeDiagnosisLines[i];
//			
//			int index =0;
//			List<String> linesThatFitPdf = breakLinesToFitPdfPage(aLine);
//			for (String aLineThatFits : linesThatFitPdf) {
//				index++;
//				//int line =  index * 10;
//				int line =  10;
//				contentStream.beginText();
//					contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
//					currentRowPosition =  currentRowPosition - line;
//					contentStream.newLineAtOffset( FIRST_COL, currentRowPosition);
//					contentStream.showText(  aLineThatFits );
//				contentStream.endText();
//			}
//		}
		
		
//		contentStream.beginText();
//			contentStream.setFont( boldFont, normalFontSize );
//			contentStream.newLineAtOffset( firstCol, tentativeDiagnosisRow );
//			contentStream.showText( "Tentative Diagnosis" );
//		contentStream.endText();
//		contentStream.beginText();
//			contentStream.setFont( boldFont, normalFontSize );
//			contentStream.newLineAtOffset( firstCol, tentativeDiagnosisRow - 10);
//			contentStream.showText( diagnosis );
//		contentStream.endText();
//		
//					
//		contentStream.beginText();
//			contentStream.setFont( boldFont, normalFontSize );
//			contentStream.newLineAtOffset( firstCol, radioInterpretationRow );
//			contentStream.showText( "Radiographic Interpretation" );
//		contentStream.endText();
//		contentStream.beginText();
//			contentStream.setFont( boldFont, normalFontSize );
//			contentStream.newLineAtOffset( firstCol, radioInterpretationRow - 10);
//			contentStream.showText( radiographicInterpretation );
//		contentStream.endText();	
//		
//		contentStream.beginText();
//			contentStream.setFont( boldFont, normalFontSize );
//			contentStream.newLineAtOffset( firstCol, radioImpressionRow );
//			contentStream.showText( "Radiographic Impression" );
//		contentStream.endText();
//		contentStream.beginText();
//			contentStream.setFont( boldFont, normalFontSize );
//			contentStream.newLineAtOffset( firstCol, radioImpressionRow - 10);
//			contentStream.showText( radiographicImpression );
//		contentStream.endText();
//		
//		contentStream.beginText();
//			contentStream.setFont( boldFont, normalFontSize );
//			contentStream.newLineAtOffset( firstCol, recommendationRow );
//			contentStream.showText( "Recommendation" );
//		contentStream.endText();
//		contentStream.beginText();
//			contentStream.setFont( boldFont, normalFontSize );
//			contentStream.newLineAtOffset( firstCol, recommendationRow - 10);
//			contentStream.showText( recommendation );
//		contentStream.endText();
		
		
		
		// Make sure that the content stream is closed:
		contentStream.close();
	
		// Save the results and ensure that the document is properly closed:
		document.save( "src/main/resources/public/"+caseNumber+".pdf");
		document.close();
	}


	private int doExam( 
			PDPageContentStream contentStream, String title, String text,  int rowPosition) throws IOException {
		
		contentStream.beginText();
			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
			contentStream.newLineAtOffset( FIRST_COL, rowPosition );
			contentStream.showText( title );
		contentStream.endText();
		
		if(StringUtils.isEmpty(text)){
			rowPosition = rowPosition -10;
			return rowPosition;
		}
		String examLines[] = text.split("\\r?\\n");
		for (int i = 0; i < examLines.length; i++) {
			
			String aLine = examLines[i];
			
			List<String> linesThatFitPdf = breakLinesToFitPdfPage(aLine);
			for (String aLineThatFits : linesThatFitPdf) {
				int line =  10;
				contentStream.beginText();
					contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
					rowPosition =  rowPosition - line;
					contentStream.newLineAtOffset( FIRST_COL, rowPosition);
					contentStream.showText(  aLineThatFits );
				contentStream.endText();
			}
		}
		return rowPosition;
	}
	
	
	
	
	private int doAmendments( 
			PDPageContentStream contentStream, String title, List<Amendment> amendments ,  int rowPosition) throws IOException {
		
//		contentStream.beginText();
//			contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
//			contentStream.newLineAtOffset( FIRST_COL, rowPosition );
//			contentStream.showText( title );
//		contentStream.endText();
		int amendmentIndex =0;
		int line =  10;
//		for (int i = 0; i < examLines.length; i++) {
		for (Amendment anAmendment : amendments) {	
			amendmentIndex++;
			int index=0;
			index++;
			String label = "Amendment #"+ amendmentIndex+" " +anAmendment.getHospitalName() + ", "+ 
					 		anAmendment.getFirstName() + " " + 
					 		anAmendment.getLastName() ;
			
			
			if( index==1){
				contentStream.beginText();
					contentStream.setFont( BOLD_FONT, NORMAL_SIZE );
					rowPosition =  rowPosition - line - 3;
					contentStream.newLineAtOffset( FIRST_COL, rowPosition);
					contentStream.showText( label);
				contentStream.endText();
			}
			
			String lines[] = anAmendment.getNewAmendment().split("\\r?\\n");
			for (int i = 0; i < lines.length; i++) {
				
				String aLine = lines[i];
				List<String> linesThatFitPdf = breakLinesToFitPdfPage(aLine);
				for (String aLineThatFits : linesThatFitPdf) {
					contentStream.beginText();
						contentStream.setFont( NORMAL_FONT, NORMAL_SIZE );
						rowPosition =  rowPosition - line;
						contentStream.newLineAtOffset( FIRST_COL, rowPosition);
						System.err.println(i+ " ====>>>>>>>> " + aLineThatFits);
						contentStream.showText(  aLineThatFits );
					contentStream.endText();
				}
				
			}
			
		}
		return rowPosition;
	}
	
	
	private List<String> breakLinesToFitPdfPage(String line){
		
		int min= 135;
		int max =169;
		List<String> lines = new ArrayList<String>();
		
		if(line.length() <= max){
			lines.add(line);
			return lines;
		}
		
		String[] lineArray = line.split(" ");
		String aLine = "";
		int waitUntil = 0;
		for (int i = 0; i < lineArray.length; i++) {
			
			aLine =aLine+ lineArray[i] + " ";
			if ( aLine.length() > min){
				String nextWord ="";
				if(lineArray.length < i+1){
					nextWord = lineArray[i+1];
				}
				
				
				if (aLine.length() + nextWord.length() > max){
					lines.add(aLine);
					aLine = "";
				}
				else {
					if( i == waitUntil){
						lines.add(aLine);
						aLine = "";
					}else{
						
						waitUntil = i+1;
					}
				}
				
			}
		}
		
		lines.add(aLine);
		return lines;
	}
}
