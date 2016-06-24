<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>MGRB Beacon</title>
</head>
<body>
<p>This is <b>MGRB Beacon</b>.<br><br>
    It provides XML, JSON and plaintext responses based on the header supplied
    by the client in its GET request, e.g.: "<i>Accept: application/json</i>".</p>
<p>Example queries:</p>
<ul>
    <li><a href="<%= request.getRequestURL()%>beacon/"><%= request.getRequestURL()%>beacon/</a></li>
    <li><a href="<%= request.getRequestURL()%>beacon/query?chrom=1&pos=52066&allele=C&ref=hg19">
        <%= request.getRequestURL()%>beacon/query?chrom=1&pos=52066&allele=C&ref=hg19
    </a></li>
    <li><a href="<%= request.getRequestURL()%>beacon/query?chrom=1&pos=52066&allele=T&ref=hg19">
        <%= request.getRequestURL()%>beacon/query?chrom=1&pos=52066&allele=T&ref=hg19
    </a></li>
</ul>
</body>
</html>
