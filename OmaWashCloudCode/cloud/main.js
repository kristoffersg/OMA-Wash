
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});

// Our cloud functioons


Parse.Cloud.define("onSlotRequest", function(request,response){
    var day  = request.params.slotday;
    var hour = request.params.slothour;
    day.push()
});
