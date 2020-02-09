package com.glinevg.subanagram.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

public class ResourceUtil {

    public static Path resource(String name) throws URISyntaxException {
        URI uri = requireNonNull(Thread.currentThread().getContextClassLoader().getResource(name)).toURI();
        return Paths.get(uri);
    }

}
