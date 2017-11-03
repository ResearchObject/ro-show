package org.researchobject.roshow.service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.Files.*;

/**
 * Extracts files and directories of a standard zip file to a destination directory. Requires at least Java 7.
 */
public final class Unzipper {

    public void unzip(final Path zipFile, final Path destDir) throws IOException {
        if (notExists(destDir)) {
            createDirectories(destDir);
        }

        try (FileSystem zipFileSystem = FileSystems.newFileSystem(zipFile, null)) {
            final Path root = zipFileSystem.getRootDirectories().iterator().next();

            walkFileTree(root, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file,
                                                 BasicFileAttributes attrs) throws IOException {
                    final Path destFile = Paths.get(destDir.toString(), file.toString());
                    try {
                        copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
                    } catch (DirectoryNotEmptyException ignore) {
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir,
                                                         BasicFileAttributes attrs) throws IOException {
                    final Path dirToCreate = Paths.get(destDir.toString(), dir.toString());
                    if (notExists(dirToCreate)) {
                        createDirectory(dirToCreate);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}