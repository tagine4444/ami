package ami.domain.services;

import java.io.IOException;

import ami.infrastructure.database.model.AmiRequestView;

public interface PdfGeneratorService {

	void generatePdf(AmiRequestView amiRequestView) throws IOException;

}
