package com.rajievtimal.wikipediaimagesearch.base;

import java.net.InterfaceAddress;

/**
 * Created by rajievtimal on 6/13/17 at 1:52 AM
 * Copyright 2017 Readfeed, Inc.
 */

//TODO: Namespace Constants. E.g. API.Parameters.Values.Generator, API.Parameters.Name.Generator

interface Constants {

    String URL = "https://en.wikipedia.org/w/api.php";

    //Parameters Names
    String ACTION = "action";
    String PROP = "prop";
    String FORMAT = "format";
    String PIPROP = "piprop";
    String PITHUMBSIZE = "pithumbsize";
    String PILIMIT = "pilimit";
    String GENERATOR = "generator";
    String GPSSEARCH = "gpssearch";
    String GPSLIMIT = "gpslimit";

    //Parameter Values
    String ACTION_VALUE = "query";
    String PROP_VALUE = "thumbnail";
    String FORMAT_VALUE = "json";
    String PIPROP_VALUE = "thumbnail";
    String PITHUMBSIZE_VALUE = "512";
    String PILIMIT_VALUE = "50";
    String GENERATOR_VALUE_PREFIX = "prefixsearch";
    String GENERATOR_VALUE_RANDOM = "random";
    String GPS_SEARCH_VALUE = "cat";
    String GPS_LIMIT_VALUE = "50";
}

