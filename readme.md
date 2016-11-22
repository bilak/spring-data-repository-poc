Sample application demonstrating process of (rest) querying specified attributes of target entity using *Spring DATA*.   
It's possible to use paging and sort to get needed number of results in specified order. There is also a place to use JPA Specifications.


After application start put this url `http://localhost:8080/users/tuple-json?attributes=id,username,userEmail&page=0&size=10` to browser. It will return all attributes. If you are interested only in id attribute just change url to `http://localhost:8080/users/tuple-json?attributes=id`.