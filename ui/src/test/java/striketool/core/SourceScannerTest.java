package striketool.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import striketool.backend.components.drummodule.Simulator;
import striketool.backend.components.repository.Repository;
import striketool.backend.usecases.SourceScanner;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

@ExtendWith(MockitoExtension.class)
class SourceScannerTest {
    @Mock
    private Repository repository;

    @Test
    void test() throws IOException {
        File zipFile = new File("src/test/resources/simulator.zip");
        Simulator zipSource = new Simulator(zipFile);

        SourceScanner sourceScanner = new SourceScanner(repository);
        sourceScanner.scan();
    }
}