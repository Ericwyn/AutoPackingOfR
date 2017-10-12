# Auto Packing Of R

APOR is a tool for automacic packing customized R language functions, work in LINUX with R CMD.

# About Package

## the model of DESCRIPTION file
    
    Package: once
    Version: 0.1
    Date: 2017-10-11
    Title: Once Test
    Author: Ericwyn Chen <address@address.com>
    Maintainer: Ericwyn Chen <address@address.com>
    Depends: R (>= 1.9.0)
    Description: A Once Test Description
    License: GPL version 2 or later

## the model of NAMESPACE file

    exportPattern( "." )
    
# How to use
 - input the `file path` of R package directory. (This directory must be empty or have not created)
 - input the `R language file path` one or more
 - input some value in DESCRIPTION file
 - run R CMD check 
 - run R CMD build
 - if build successful , you can see a file like `XXXX.tar.gz`,it is your R package.
 
 PS : the log of my test build is in `build.log` .
 PPS : maybe have some bug , like that the process may stop in R CMD check , you can restart it and try to build again.