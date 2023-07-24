# Store the API GET response in a variable ($Posts).
$Posts = Invoke-RestMethod -Uri "http://www.foo.com/bar"

# Run the GetType() method against the first item in the array, identified by its index of 0.
$Posts[0].GetType()

# This will prompt for credentials and store them in a PSCredential object.
$Cred = Get-Credential



# Post json request.
 $Params = @{
     Method = "Post"
     Uri = "http://www.foo.com/bar"
     Body = $JsonBody
     ContentType = "application/json"
 }
 Invoke-RestMethod @Params


# Send a GET request including Basic authentication.
<#
$Params = @{
	Uri = "http://www.foo.com/bar"
	Authentication = "Basic"
	Credential = $Cred
}

Invoke-RestMethod @Params
#>