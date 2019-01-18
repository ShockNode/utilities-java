package com.shocknode.utilities;

import java.util.Optional;
import java.util.stream.Stream;

public class Streams {

    public static <T> Optional<T> findLast(Stream<T> stream){
      return stream.reduce((first, second) -> second);
    }

}
