/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
$(document).ready(function() {

    $(".click-title").mouseenter( function(    e){
        e.preventDefault();
        this.style.cursor="pointer";
    });
    $(".click-title").mousedown( function(event){
        event.preventDefault();
    });

    // Ugly code while this script is shared among several pages
    try{
        refreshHitsPerSecond(true);
    } catch(e){}
    try{
        refreshResponseTimeOverTime(true);
    } catch(e){}
    try{
        refreshResponseTimePercentiles();
    } catch(e){}
});


var responseTimePercentilesInfos = {
        data: {"result": {"minY": 0.0, "minX": 0.0, "maxY": 1521.0, "series": [{"data": [[0.0, 0.0], [0.1, 0.0], [0.2, 0.0], [0.3, 0.0], [0.4, 0.0], [0.5, 0.0], [0.6, 0.0], [0.7, 0.0], [0.8, 0.0], [0.9, 0.0], [1.0, 0.0], [1.1, 0.0], [1.2, 1.0], [1.3, 1.0], [1.4, 1.0], [1.5, 1.0], [1.6, 1.0], [1.7, 1.0], [1.8, 1.0], [1.9, 1.0], [2.0, 1.0], [2.1, 1.0], [2.2, 1.0], [2.3, 1.0], [2.4, 1.0], [2.5, 1.0], [2.6, 1.0], [2.7, 1.0], [2.8, 1.0], [2.9, 1.0], [3.0, 1.0], [3.1, 1.0], [3.2, 1.0], [3.3, 1.0], [3.4, 1.0], [3.5, 1.0], [3.6, 1.0], [3.7, 1.0], [3.8, 1.0], [3.9, 1.0], [4.0, 1.0], [4.1, 1.0], [4.2, 1.0], [4.3, 1.0], [4.4, 1.0], [4.5, 1.0], [4.6, 1.0], [4.7, 1.0], [4.8, 1.0], [4.9, 1.0], [5.0, 1.0], [5.1, 1.0], [5.2, 1.0], [5.3, 1.0], [5.4, 1.0], [5.5, 1.0], [5.6, 1.0], [5.7, 1.0], [5.8, 1.0], [5.9, 1.0], [6.0, 1.0], [6.1, 1.0], [6.2, 1.0], [6.3, 1.0], [6.4, 1.0], [6.5, 1.0], [6.6, 1.0], [6.7, 1.0], [6.8, 1.0], [6.9, 1.0], [7.0, 1.0], [7.1, 1.0], [7.2, 1.0], [7.3, 1.0], [7.4, 1.0], [7.5, 1.0], [7.6, 1.0], [7.7, 1.0], [7.8, 1.0], [7.9, 1.0], [8.0, 1.0], [8.1, 1.0], [8.2, 1.0], [8.3, 1.0], [8.4, 1.0], [8.5, 1.0], [8.6, 1.0], [8.7, 1.0], [8.8, 1.0], [8.9, 1.0], [9.0, 1.0], [9.1, 1.0], [9.2, 1.0], [9.3, 1.0], [9.4, 1.0], [9.5, 1.0], [9.6, 1.0], [9.7, 1.0], [9.8, 1.0], [9.9, 1.0], [10.0, 1.0], [10.1, 1.0], [10.2, 1.0], [10.3, 1.0], [10.4, 1.0], [10.5, 1.0], [10.6, 1.0], [10.7, 1.0], [10.8, 1.0], [10.9, 1.0], [11.0, 1.0], [11.1, 1.0], [11.2, 1.0], [11.3, 1.0], [11.4, 1.0], [11.5, 1.0], [11.6, 1.0], [11.7, 1.0], [11.8, 1.0], [11.9, 1.0], [12.0, 1.0], [12.1, 1.0], [12.2, 1.0], [12.3, 1.0], [12.4, 1.0], [12.5, 1.0], [12.6, 1.0], [12.7, 1.0], [12.8, 1.0], [12.9, 1.0], [13.0, 1.0], [13.1, 1.0], [13.2, 1.0], [13.3, 1.0], [13.4, 1.0], [13.5, 1.0], [13.6, 1.0], [13.7, 1.0], [13.8, 1.0], [13.9, 1.0], [14.0, 1.0], [14.1, 1.0], [14.2, 1.0], [14.3, 1.0], [14.4, 1.0], [14.5, 1.0], [14.6, 1.0], [14.7, 1.0], [14.8, 1.0], [14.9, 1.0], [15.0, 1.0], [15.1, 1.0], [15.2, 1.0], [15.3, 1.0], [15.4, 1.0], [15.5, 1.0], [15.6, 1.0], [15.7, 1.0], [15.8, 1.0], [15.9, 1.0], [16.0, 1.0], [16.1, 1.0], [16.2, 1.0], [16.3, 1.0], [16.4, 1.0], [16.5, 1.0], [16.6, 1.0], [16.7, 1.0], [16.8, 1.0], [16.9, 1.0], [17.0, 1.0], [17.1, 1.0], [17.2, 1.0], [17.3, 1.0], [17.4, 1.0], [17.5, 1.0], [17.6, 1.0], [17.7, 1.0], [17.8, 1.0], [17.9, 1.0], [18.0, 1.0], [18.1, 1.0], [18.2, 1.0], [18.3, 1.0], [18.4, 1.0], [18.5, 1.0], [18.6, 1.0], [18.7, 1.0], [18.8, 1.0], [18.9, 1.0], [19.0, 1.0], [19.1, 1.0], [19.2, 1.0], [19.3, 1.0], [19.4, 1.0], [19.5, 1.0], [19.6, 1.0], [19.7, 1.0], [19.8, 1.0], [19.9, 1.0], [20.0, 1.0], [20.1, 1.0], [20.2, 1.0], [20.3, 1.0], [20.4, 1.0], [20.5, 1.0], [20.6, 1.0], [20.7, 1.0], [20.8, 1.0], [20.9, 1.0], [21.0, 1.0], [21.1, 1.0], [21.2, 1.0], [21.3, 1.0], [21.4, 1.0], [21.5, 1.0], [21.6, 1.0], [21.7, 1.0], [21.8, 1.0], [21.9, 1.0], [22.0, 1.0], [22.1, 1.0], [22.2, 1.0], [22.3, 1.0], [22.4, 1.0], [22.5, 1.0], [22.6, 1.0], [22.7, 1.0], [22.8, 1.0], [22.9, 1.0], [23.0, 1.0], [23.1, 1.0], [23.2, 1.0], [23.3, 1.0], [23.4, 1.0], [23.5, 1.0], [23.6, 1.0], [23.7, 1.0], [23.8, 1.0], [23.9, 1.0], [24.0, 1.0], [24.1, 1.0], [24.2, 1.0], [24.3, 1.0], [24.4, 1.0], [24.5, 1.0], [24.6, 1.0], [24.7, 1.0], [24.8, 1.0], [24.9, 1.0], [25.0, 1.0], [25.1, 1.0], [25.2, 1.0], [25.3, 1.0], [25.4, 1.0], [25.5, 1.0], [25.6, 1.0], [25.7, 1.0], [25.8, 1.0], [25.9, 1.0], [26.0, 1.0], [26.1, 1.0], [26.2, 1.0], [26.3, 1.0], [26.4, 1.0], [26.5, 1.0], [26.6, 1.0], [26.7, 1.0], [26.8, 1.0], [26.9, 1.0], [27.0, 1.0], [27.1, 1.0], [27.2, 1.0], [27.3, 1.0], [27.4, 1.0], [27.5, 1.0], [27.6, 1.0], [27.7, 1.0], [27.8, 1.0], [27.9, 1.0], [28.0, 1.0], [28.1, 1.0], [28.2, 1.0], [28.3, 1.0], [28.4, 1.0], [28.5, 1.0], [28.6, 1.0], [28.7, 1.0], [28.8, 1.0], [28.9, 1.0], [29.0, 1.0], [29.1, 1.0], [29.2, 1.0], [29.3, 1.0], [29.4, 1.0], [29.5, 1.0], [29.6, 1.0], [29.7, 1.0], [29.8, 1.0], [29.9, 1.0], [30.0, 1.0], [30.1, 1.0], [30.2, 1.0], [30.3, 1.0], [30.4, 1.0], [30.5, 1.0], [30.6, 1.0], [30.7, 1.0], [30.8, 1.0], [30.9, 1.0], [31.0, 1.0], [31.1, 1.0], [31.2, 1.0], [31.3, 1.0], [31.4, 1.0], [31.5, 1.0], [31.6, 1.0], [31.7, 1.0], [31.8, 1.0], [31.9, 1.0], [32.0, 1.0], [32.1, 1.0], [32.2, 1.0], [32.3, 1.0], [32.4, 1.0], [32.5, 1.0], [32.6, 1.0], [32.7, 1.0], [32.8, 1.0], [32.9, 2.0], [33.0, 2.0], [33.1, 2.0], [33.2, 2.0], [33.3, 2.0], [33.4, 2.0], [33.5, 2.0], [33.6, 2.0], [33.7, 2.0], [33.8, 2.0], [33.9, 2.0], [34.0, 2.0], [34.1, 2.0], [34.2, 2.0], [34.3, 2.0], [34.4, 2.0], [34.5, 2.0], [34.6, 2.0], [34.7, 2.0], [34.8, 2.0], [34.9, 2.0], [35.0, 2.0], [35.1, 2.0], [35.2, 2.0], [35.3, 2.0], [35.4, 2.0], [35.5, 2.0], [35.6, 2.0], [35.7, 2.0], [35.8, 2.0], [35.9, 2.0], [36.0, 2.0], [36.1, 2.0], [36.2, 2.0], [36.3, 2.0], [36.4, 2.0], [36.5, 2.0], [36.6, 2.0], [36.7, 2.0], [36.8, 2.0], [36.9, 2.0], [37.0, 2.0], [37.1, 2.0], [37.2, 2.0], [37.3, 2.0], [37.4, 2.0], [37.5, 2.0], [37.6, 2.0], [37.7, 2.0], [37.8, 2.0], [37.9, 2.0], [38.0, 2.0], [38.1, 2.0], [38.2, 2.0], [38.3, 2.0], [38.4, 2.0], [38.5, 2.0], [38.6, 2.0], [38.7, 2.0], [38.8, 2.0], [38.9, 2.0], [39.0, 2.0], [39.1, 2.0], [39.2, 2.0], [39.3, 2.0], [39.4, 2.0], [39.5, 2.0], [39.6, 2.0], [39.7, 2.0], [39.8, 2.0], [39.9, 2.0], [40.0, 2.0], [40.1, 2.0], [40.2, 2.0], [40.3, 2.0], [40.4, 2.0], [40.5, 2.0], [40.6, 2.0], [40.7, 2.0], [40.8, 2.0], [40.9, 2.0], [41.0, 2.0], [41.1, 2.0], [41.2, 2.0], [41.3, 2.0], [41.4, 2.0], [41.5, 2.0], [41.6, 2.0], [41.7, 2.0], [41.8, 2.0], [41.9, 2.0], [42.0, 2.0], [42.1, 2.0], [42.2, 2.0], [42.3, 2.0], [42.4, 2.0], [42.5, 2.0], [42.6, 2.0], [42.7, 2.0], [42.8, 2.0], [42.9, 2.0], [43.0, 2.0], [43.1, 2.0], [43.2, 2.0], [43.3, 2.0], [43.4, 2.0], [43.5, 2.0], [43.6, 2.0], [43.7, 2.0], [43.8, 2.0], [43.9, 2.0], [44.0, 2.0], [44.1, 2.0], [44.2, 2.0], [44.3, 2.0], [44.4, 2.0], [44.5, 2.0], [44.6, 2.0], [44.7, 2.0], [44.8, 2.0], [44.9, 2.0], [45.0, 2.0], [45.1, 2.0], [45.2, 2.0], [45.3, 2.0], [45.4, 2.0], [45.5, 2.0], [45.6, 2.0], [45.7, 2.0], [45.8, 2.0], [45.9, 2.0], [46.0, 2.0], [46.1, 2.0], [46.2, 2.0], [46.3, 2.0], [46.4, 2.0], [46.5, 2.0], [46.6, 2.0], [46.7, 2.0], [46.8, 2.0], [46.9, 2.0], [47.0, 2.0], [47.1, 2.0], [47.2, 2.0], [47.3, 2.0], [47.4, 2.0], [47.5, 2.0], [47.6, 2.0], [47.7, 2.0], [47.8, 2.0], [47.9, 2.0], [48.0, 2.0], [48.1, 2.0], [48.2, 2.0], [48.3, 2.0], [48.4, 2.0], [48.5, 2.0], [48.6, 2.0], [48.7, 2.0], [48.8, 2.0], [48.9, 2.0], [49.0, 2.0], [49.1, 2.0], [49.2, 2.0], [49.3, 2.0], [49.4, 2.0], [49.5, 2.0], [49.6, 2.0], [49.7, 2.0], [49.8, 2.0], [49.9, 2.0], [50.0, 2.0], [50.1, 2.0], [50.2, 2.0], [50.3, 2.0], [50.4, 2.0], [50.5, 2.0], [50.6, 2.0], [50.7, 2.0], [50.8, 2.0], [50.9, 2.0], [51.0, 2.0], [51.1, 2.0], [51.2, 2.0], [51.3, 2.0], [51.4, 2.0], [51.5, 2.0], [51.6, 2.0], [51.7, 2.0], [51.8, 2.0], [51.9, 2.0], [52.0, 2.0], [52.1, 2.0], [52.2, 2.0], [52.3, 2.0], [52.4, 2.0], [52.5, 2.0], [52.6, 2.0], [52.7, 2.0], [52.8, 2.0], [52.9, 2.0], [53.0, 2.0], [53.1, 2.0], [53.2, 2.0], [53.3, 2.0], [53.4, 2.0], [53.5, 2.0], [53.6, 2.0], [53.7, 2.0], [53.8, 2.0], [53.9, 2.0], [54.0, 2.0], [54.1, 2.0], [54.2, 2.0], [54.3, 2.0], [54.4, 2.0], [54.5, 2.0], [54.6, 2.0], [54.7, 2.0], [54.8, 2.0], [54.9, 2.0], [55.0, 2.0], [55.1, 2.0], [55.2, 2.0], [55.3, 2.0], [55.4, 2.0], [55.5, 2.0], [55.6, 2.0], [55.7, 2.0], [55.8, 2.0], [55.9, 2.0], [56.0, 2.0], [56.1, 2.0], [56.2, 2.0], [56.3, 2.0], [56.4, 2.0], [56.5, 2.0], [56.6, 2.0], [56.7, 2.0], [56.8, 2.0], [56.9, 2.0], [57.0, 2.0], [57.1, 2.0], [57.2, 2.0], [57.3, 2.0], [57.4, 2.0], [57.5, 2.0], [57.6, 2.0], [57.7, 2.0], [57.8, 2.0], [57.9, 2.0], [58.0, 2.0], [58.1, 2.0], [58.2, 2.0], [58.3, 2.0], [58.4, 2.0], [58.5, 2.0], [58.6, 2.0], [58.7, 2.0], [58.8, 2.0], [58.9, 2.0], [59.0, 2.0], [59.1, 2.0], [59.2, 2.0], [59.3, 2.0], [59.4, 2.0], [59.5, 2.0], [59.6, 2.0], [59.7, 2.0], [59.8, 2.0], [59.9, 2.0], [60.0, 2.0], [60.1, 2.0], [60.2, 2.0], [60.3, 2.0], [60.4, 2.0], [60.5, 2.0], [60.6, 2.0], [60.7, 2.0], [60.8, 2.0], [60.9, 2.0], [61.0, 2.0], [61.1, 2.0], [61.2, 2.0], [61.3, 2.0], [61.4, 2.0], [61.5, 2.0], [61.6, 2.0], [61.7, 2.0], [61.8, 2.0], [61.9, 2.0], [62.0, 2.0], [62.1, 2.0], [62.2, 2.0], [62.3, 2.0], [62.4, 2.0], [62.5, 2.0], [62.6, 2.0], [62.7, 2.0], [62.8, 2.0], [62.9, 2.0], [63.0, 2.0], [63.1, 2.0], [63.2, 2.0], [63.3, 2.0], [63.4, 2.0], [63.5, 2.0], [63.6, 2.0], [63.7, 2.0], [63.8, 3.0], [63.9, 3.0], [64.0, 3.0], [64.1, 3.0], [64.2, 3.0], [64.3, 3.0], [64.4, 3.0], [64.5, 3.0], [64.6, 3.0], [64.7, 3.0], [64.8, 3.0], [64.9, 3.0], [65.0, 3.0], [65.1, 3.0], [65.2, 3.0], [65.3, 3.0], [65.4, 3.0], [65.5, 3.0], [65.6, 3.0], [65.7, 3.0], [65.8, 3.0], [65.9, 3.0], [66.0, 3.0], [66.1, 3.0], [66.2, 3.0], [66.3, 3.0], [66.4, 3.0], [66.5, 3.0], [66.6, 3.0], [66.7, 3.0], [66.8, 3.0], [66.9, 3.0], [67.0, 3.0], [67.1, 3.0], [67.2, 3.0], [67.3, 3.0], [67.4, 3.0], [67.5, 3.0], [67.6, 3.0], [67.7, 3.0], [67.8, 3.0], [67.9, 3.0], [68.0, 3.0], [68.1, 3.0], [68.2, 3.0], [68.3, 3.0], [68.4, 3.0], [68.5, 3.0], [68.6, 3.0], [68.7, 3.0], [68.8, 3.0], [68.9, 3.0], [69.0, 3.0], [69.1, 3.0], [69.2, 3.0], [69.3, 3.0], [69.4, 3.0], [69.5, 3.0], [69.6, 3.0], [69.7, 3.0], [69.8, 3.0], [69.9, 3.0], [70.0, 3.0], [70.1, 3.0], [70.2, 3.0], [70.3, 3.0], [70.4, 3.0], [70.5, 3.0], [70.6, 3.0], [70.7, 3.0], [70.8, 3.0], [70.9, 3.0], [71.0, 3.0], [71.1, 3.0], [71.2, 3.0], [71.3, 3.0], [71.4, 3.0], [71.5, 3.0], [71.6, 3.0], [71.7, 3.0], [71.8, 3.0], [71.9, 3.0], [72.0, 3.0], [72.1, 3.0], [72.2, 3.0], [72.3, 3.0], [72.4, 3.0], [72.5, 3.0], [72.6, 3.0], [72.7, 3.0], [72.8, 3.0], [72.9, 3.0], [73.0, 3.0], [73.1, 3.0], [73.2, 3.0], [73.3, 3.0], [73.4, 3.0], [73.5, 3.0], [73.6, 3.0], [73.7, 3.0], [73.8, 3.0], [73.9, 3.0], [74.0, 3.0], [74.1, 3.0], [74.2, 3.0], [74.3, 3.0], [74.4, 3.0], [74.5, 3.0], [74.6, 3.0], [74.7, 3.0], [74.8, 3.0], [74.9, 3.0], [75.0, 3.0], [75.1, 3.0], [75.2, 3.0], [75.3, 3.0], [75.4, 3.0], [75.5, 3.0], [75.6, 3.0], [75.7, 3.0], [75.8, 3.0], [75.9, 3.0], [76.0, 3.0], [76.1, 3.0], [76.2, 3.0], [76.3, 3.0], [76.4, 3.0], [76.5, 3.0], [76.6, 3.0], [76.7, 3.0], [76.8, 3.0], [76.9, 3.0], [77.0, 3.0], [77.1, 3.0], [77.2, 3.0], [77.3, 3.0], [77.4, 3.0], [77.5, 3.0], [77.6, 3.0], [77.7, 3.0], [77.8, 3.0], [77.9, 3.0], [78.0, 3.0], [78.1, 3.0], [78.2, 3.0], [78.3, 3.0], [78.4, 3.0], [78.5, 3.0], [78.6, 3.0], [78.7, 3.0], [78.8, 3.0], [78.9, 3.0], [79.0, 3.0], [79.1, 3.0], [79.2, 3.0], [79.3, 3.0], [79.4, 3.0], [79.5, 3.0], [79.6, 3.0], [79.7, 3.0], [79.8, 3.0], [79.9, 3.0], [80.0, 3.0], [80.1, 3.0], [80.2, 3.0], [80.3, 3.0], [80.4, 3.0], [80.5, 3.0], [80.6, 3.0], [80.7, 3.0], [80.8, 3.0], [80.9, 3.0], [81.0, 3.0], [81.1, 3.0], [81.2, 3.0], [81.3, 3.0], [81.4, 3.0], [81.5, 3.0], [81.6, 3.0], [81.7, 3.0], [81.8, 3.0], [81.9, 3.0], [82.0, 3.0], [82.1, 3.0], [82.2, 3.0], [82.3, 3.0], [82.4, 3.0], [82.5, 3.0], [82.6, 3.0], [82.7, 3.0], [82.8, 3.0], [82.9, 3.0], [83.0, 3.0], [83.1, 3.0], [83.2, 3.0], [83.3, 3.0], [83.4, 3.0], [83.5, 3.0], [83.6, 3.0], [83.7, 3.0], [83.8, 3.0], [83.9, 3.0], [84.0, 3.0], [84.1, 3.0], [84.2, 3.0], [84.3, 3.0], [84.4, 3.0], [84.5, 3.0], [84.6, 3.0], [84.7, 3.0], [84.8, 3.0], [84.9, 3.0], [85.0, 3.0], [85.1, 3.0], [85.2, 3.0], [85.3, 3.0], [85.4, 3.0], [85.5, 3.0], [85.6, 3.0], [85.7, 3.0], [85.8, 3.0], [85.9, 3.0], [86.0, 3.0], [86.1, 3.0], [86.2, 3.0], [86.3, 3.0], [86.4, 3.0], [86.5, 3.0], [86.6, 3.0], [86.7, 3.0], [86.8, 3.0], [86.9, 3.0], [87.0, 3.0], [87.1, 3.0], [87.2, 3.0], [87.3, 3.0], [87.4, 3.0], [87.5, 3.0], [87.6, 3.0], [87.7, 3.0], [87.8, 3.0], [87.9, 4.0], [88.0, 4.0], [88.1, 4.0], [88.2, 4.0], [88.3, 4.0], [88.4, 4.0], [88.5, 4.0], [88.6, 4.0], [88.7, 4.0], [88.8, 4.0], [88.9, 4.0], [89.0, 4.0], [89.1, 4.0], [89.2, 4.0], [89.3, 4.0], [89.4, 4.0], [89.5, 4.0], [89.6, 4.0], [89.7, 4.0], [89.8, 4.0], [89.9, 4.0], [90.0, 4.0], [90.1, 4.0], [90.2, 4.0], [90.3, 4.0], [90.4, 4.0], [90.5, 4.0], [90.6, 4.0], [90.7, 4.0], [90.8, 4.0], [90.9, 4.0], [91.0, 4.0], [91.1, 4.0], [91.2, 4.0], [91.3, 4.0], [91.4, 4.0], [91.5, 4.0], [91.6, 4.0], [91.7, 4.0], [91.8, 4.0], [91.9, 4.0], [92.0, 4.0], [92.1, 4.0], [92.2, 4.0], [92.3, 4.0], [92.4, 4.0], [92.5, 4.0], [92.6, 4.0], [92.7, 4.0], [92.8, 4.0], [92.9, 4.0], [93.0, 4.0], [93.1, 4.0], [93.2, 4.0], [93.3, 4.0], [93.4, 4.0], [93.5, 4.0], [93.6, 4.0], [93.7, 4.0], [93.8, 4.0], [93.9, 4.0], [94.0, 4.0], [94.1, 4.0], [94.2, 4.0], [94.3, 4.0], [94.4, 4.0], [94.5, 4.0], [94.6, 4.0], [94.7, 4.0], [94.8, 4.0], [94.9, 4.0], [95.0, 4.0], [95.1, 4.0], [95.2, 4.0], [95.3, 4.0], [95.4, 4.0], [95.5, 4.0], [95.6, 4.0], [95.7, 4.0], [95.8, 4.0], [95.9, 5.0], [96.0, 5.0], [96.1, 5.0], [96.2, 5.0], [96.3, 5.0], [96.4, 5.0], [96.5, 5.0], [96.6, 5.0], [96.7, 5.0], [96.8, 5.0], [96.9, 5.0], [97.0, 5.0], [97.1, 5.0], [97.2, 5.0], [97.3, 6.0], [97.4, 6.0], [97.5, 6.0], [97.6, 6.0], [97.7, 6.0], [97.8, 6.0], [97.9, 7.0], [98.0, 7.0], [98.1, 7.0], [98.2, 7.0], [98.3, 7.0], [98.4, 8.0], [98.5, 8.0], [98.6, 8.0], [98.7, 9.0], [98.8, 9.0], [98.9, 9.0], [99.0, 10.0], [99.1, 10.0], [99.2, 11.0], [99.3, 12.0], [99.4, 13.0], [99.5, 14.0], [99.6, 15.0], [99.7, 17.0], [99.8, 21.0], [99.9, 38.0]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 100.0, "title": "Response Time Percentiles"}},
        getOptions: function() {
            return {
                series: {
                    points: { show: false }
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentiles'
                },
                xaxis: {
                    tickDecimals: 1,
                    axisLabel: "Percentiles",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Percentile value in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : %x.2 percentile was %y ms"
                },
                selection: { mode: "xy" },
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentiles"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesPercentiles"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesPercentiles"), dataset, prepareOverviewOptions(options));
        }
};

/**
 * @param elementId Id of element where we display message
 */
function setEmptyGraph(elementId) {
    $(function() {
        $(elementId).text("No graph series with filter="+seriesFilter);
    });
}

// Response times percentiles
function refreshResponseTimePercentiles() {
    var infos = responseTimePercentilesInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimePercentiles");
        return;
    }
    if (isGraph($("#flotResponseTimesPercentiles"))){
        infos.createGraph();
    } else {
        var choiceContainer = $("#choicesResponseTimePercentiles");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesPercentiles", "#overviewResponseTimesPercentiles");
        $('#bodyResponseTimePercentiles .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimeDistributionInfos = {
        data: {"result": {"minY": 1.0, "minX": 0.0, "maxY": 4.9992296E7, "series": [{"data": [[0.0, 4.9992296E7], [600.0, 42.0], [700.0, 16.0], [200.0, 1646.0], [800.0, 13.0], [900.0, 6.0], [1000.0, 3.0], [1100.0, 4.0], [300.0, 243.0], [1200.0, 1.0], [1300.0, 2.0], [1500.0, 1.0], [100.0, 5568.0], [400.0, 102.0], [500.0, 57.0]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 1500.0, "title": "Response Time Distribution"}},
        getOptions: function() {
            var granularity = this.data.result.granularity;
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    barWidth: this.data.result.granularity
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " responses for " + label + " were between " + xval + " and " + (xval + granularity) + " ms";
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimeDistribution"), prepareData(data.result.series, $("#choicesResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshResponseTimeDistribution() {
    var infos = responseTimeDistributionInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeDistribution");
        return;
    }
    if (isGraph($("#flotResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var syntheticResponseTimeDistributionInfos = {
        data: {"result": {"minY": 1.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 4.9999855E7, "series": [{"data": [[0.0, 4.9999855E7]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [[1.0, 144.0]], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [[2.0, 1.0]], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 2.0, "title": "Synthetic Response Times Distribution"}},
        getOptions: function() {
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendSyntheticResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times ranges",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                    tickLength:0,
                    min:-0.5,
                    max:3.5
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    align: "center",
                    barWidth: 0.25,
                    fill:.75
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " " + label;
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            options.xaxis.ticks = data.result.ticks;
            $.plot($("#flotSyntheticResponseTimeDistribution"), prepareData(data.result.series, $("#choicesSyntheticResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshSyntheticResponseTimeDistribution() {
    var infos = syntheticResponseTimeDistributionInfos;
    prepareSeries(infos.data, true);
    if (isGraph($("#flotSyntheticResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerSyntheticResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var activeThreadsOverTimeInfos = {
        data: {"result": {"minY": 8.854846797895304, "minX": 1.6414626E12, "maxY": 50.0, "series": [{"data": [[1.64146362E12, 50.0], [1.64146392E12, 50.0], [1.64146302E12, 50.0], [1.64146524E12, 8.854846797895304], [1.64146332E12, 50.0], [1.64146494E12, 50.0], [1.6414629E12, 50.0], [1.64146512E12, 17.0477498413218], [1.6414632E12, 50.0], [1.64146482E12, 50.0], [1.64146452E12, 50.0], [1.6414626E12, 49.47548288544052], [1.64146422E12, 50.0], [1.6414644E12, 50.0], [1.6414641E12, 50.0], [1.6414635E12, 50.0], [1.6414638E12, 50.0], [1.64146338E12, 50.0], [1.64146368E12, 50.0], [1.641465E12, 50.0], [1.64146278E12, 50.0], [1.64146308E12, 50.0], [1.6414647E12, 50.0], [1.64146266E12, 50.0], [1.64146488E12, 50.0], [1.64146296E12, 50.0], [1.64146458E12, 50.0], [1.64146428E12, 50.0], [1.64146398E12, 50.0], [1.64146386E12, 50.0], [1.64146416E12, 50.0], [1.64146326E12, 50.0], [1.64146356E12, 50.0], [1.64146518E12, 15.924957079427791], [1.64146314E12, 50.0], [1.64146344E12, 50.0], [1.64146506E12, 43.93650511548904], [1.64146476E12, 50.0], [1.64146284E12, 50.0], [1.64146446E12, 50.0], [1.64146464E12, 50.0], [1.64146272E12, 50.0], [1.64146434E12, 50.0], [1.64146374E12, 50.0], [1.64146404E12, 50.0]], "isOverall": false, "label": "线程组", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.64146524E12, "title": "Active Threads Over Time"}},
        getOptions: function() {
            return {
                series: {
                    stack: true,
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 6,
                    show: true,
                    container: '#legendActiveThreadsOverTime'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                selection: {
                    mode: 'xy'
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : At %x there were %y active threads"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesActiveThreadsOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotActiveThreadsOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewActiveThreadsOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Active Threads Over Time
function refreshActiveThreadsOverTime(fixTimestamps) {
    var infos = activeThreadsOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotActiveThreadsOverTime"))) {
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesActiveThreadsOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotActiveThreadsOverTime", "#overviewActiveThreadsOverTime");
        $('#footerActiveThreadsOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var timeVsThreadsInfos = {
        data: {"result": {"minY": 0.43323529411764644, "minX": 1.0, "maxY": 3.14333333333333, "series": [{"data": [[2.0, 0.48335467349551975], [3.0, 0.5203687184061849], [4.0, 0.5686325906509381], [5.0, 0.5553291216629304], [6.0, 0.5882859074426894], [7.0, 0.6525085518814135], [8.0, 0.7117382271468157], [9.0, 0.6088819077538182], [10.0, 0.6560742407199089], [11.0, 0.6848311595497605], [12.0, 0.7731964674848015], [13.0, 0.7413704803851304], [14.0, 0.7445092965882398], [15.0, 0.7803952023470512], [16.0, 0.8970192857175131], [17.0, 0.952583638375757], [18.0, 0.9281345565749224], [19.0, 1.0421747681933156], [20.0, 1.0218766473378993], [21.0, 1.0998096849228172], [22.0, 1.2229095074455862], [23.0, 1.256054421768711], [24.0, 1.1226443314388286], [25.0, 1.2746160794941293], [26.0, 1.3340907861277902], [27.0, 1.4061565183827067], [28.0, 1.4947056332062703], [29.0, 1.3988208871420478], [30.0, 1.4852250431060132], [31.0, 1.4851945854483932], [32.0, 1.5596702420713768], [33.0, 1.5770258236865435], [34.0, 1.7034405651267903], [35.0, 1.6019773659992467], [36.0, 1.8277922573262775], [37.0, 1.6283300401989496], [38.0, 1.8397505990740493], [39.0, 1.7746574890594224], [40.0, 1.9167691676916783], [41.0, 1.947288095797138], [42.0, 2.1081650409854875], [43.0, 2.317511792452828], [44.0, 3.14333333333333], [45.0, 1.980981649741664], [46.0, 1.8725987762652405], [47.0, 1.8132474701011971], [48.0, 1.8990080776796558], [49.0, 1.9624239775665449], [50.0, 2.472344000983918], [1.0, 0.43323529411764644]], "isOverall": false, "label": "HTTP请求", "isController": false}, {"data": [[48.36923100000115, 2.3942964600007812]], "isOverall": false, "label": "HTTP请求-Aggregated", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 50.0, "title": "Time VS Threads"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: { noColumns: 2,show: true, container: '#legendTimeVsThreads' },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s: At %x.2 active threads, Average response time was %y.2 ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesTimeVsThreads"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotTimesVsThreads"), dataset, options);
            // setup overview
            $.plot($("#overviewTimesVsThreads"), dataset, prepareOverviewOptions(options));
        }
};

// Time vs threads
function refreshTimeVsThreads(){
    var infos = timeVsThreadsInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTimeVsThreads");
        return;
    }
    if(isGraph($("#flotTimesVsThreads"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTimeVsThreads");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTimesVsThreads", "#overviewTimesVsThreads");
        $('#footerTimeVsThreads .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var bytesThroughputOverTimeInfos = {
        data : {"result": {"minY": 479723.2166666667, "minX": 1.6414626E12, "maxY": 4905092.333333333, "series": [{"data": [[1.64146362E12, 3420967.2], [1.64146392E12, 3566171.7333333334], [1.64146302E12, 3184741.566666667], [1.64146524E12, 479723.2166666667], [1.64146332E12, 3457437.45], [1.64146494E12, 3444557.85], [1.6414629E12, 2951051.75], [1.64146512E12, 2974576.4166666665], [1.6414632E12, 3192263.6166666667], [1.64146482E12, 4146029.566666667], [1.64146452E12, 3385022.033333333], [1.6414626E12, 841256.4666666667], [1.64146422E12, 3457893.9833333334], [1.6414644E12, 3511185.5166666666], [1.6414641E12, 3632835.033333333], [1.6414635E12, 3464333.6166666667], [1.6414638E12, 3482854.716666667], [1.64146338E12, 3531875.8], [1.64146368E12, 3666956.6333333333], [1.641465E12, 3977314.5], [1.64146278E12, 3135637.0166666666], [1.64146308E12, 3022959.2666666666], [1.6414647E12, 3444028.6], [1.64146266E12, 4767071.166666667], [1.64146488E12, 3487429.05], [1.64146296E12, 3146626.5833333335], [1.64146458E12, 3446811.95], [1.64146428E12, 3554380.0166666666], [1.64146398E12, 3935105.15], [1.64146386E12, 3481596.033333333], [1.64146416E12, 3919278.466666667], [1.64146326E12, 3351777.2333333334], [1.64146356E12, 3358889.466666667], [1.64146518E12, 3086275.933333333], [1.64146314E12, 3196663.466666667], [1.64146344E12, 3528159.6333333333], [1.64146506E12, 4112212.15], [1.64146476E12, 3471987.15], [1.64146284E12, 3178267.4166666665], [1.64146446E12, 3994703.4166666665], [1.64146464E12, 3927457.716666667], [1.64146272E12, 3439409.4833333334], [1.64146434E12, 3604042.716666667], [1.64146374E12, 3435754.316666667], [1.64146404E12, 3462761.2666666666]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.64146362E12, 3520014.8666666667], [1.64146392E12, 3669421.6], [1.64146302E12, 3276949.6666666665], [1.64146524E12, 493615.93333333335], [1.64146332E12, 3557539.6666666665], [1.64146494E12, 3544288.8], [1.6414629E12, 3036494.533333333], [1.64146512E12, 3060702.6666666665], [1.6414632E12, 3284692.1333333333], [1.64146482E12, 4266070.933333334], [1.64146452E12, 3483029.0], [1.6414626E12, 865602.1333333333], [1.64146422E12, 3558009.6666666665], [1.6414644E12, 3612846.1333333333], [1.6414641E12, 3738016.533333333], [1.6414635E12, 3564636.6666666665], [1.6414638E12, 3583693.6], [1.64146338E12, 3634134.0], [1.64146368E12, 3773125.533333333], [1.641465E12, 4092468.6], [1.64146278E12, 3226421.533333333], [1.64146308E12, 3110481.933333333], [1.6414647E12, 3543743.6], [1.64146266E12, 4905092.333333333], [1.64146488E12, 3588399.8666666667], [1.64146296E12, 3237729.7333333334], [1.64146458E12, 3546607.466666667], [1.64146428E12, 3657289.3333333335], [1.64146398E12, 4049037.466666667], [1.64146386E12, 3582399.533333333], [1.64146416E12, 4032753.533333333], [1.64146326E12, 3448819.2666666666], [1.64146356E12, 3456138.7333333334], [1.64146518E12, 3175633.3333333335], [1.64146314E12, 3289216.6666666665], [1.64146344E12, 3630311.3333333335], [1.64146506E12, 4231278.4], [1.64146476E12, 3572510.7333333334], [1.64146284E12, 3270288.2], [1.64146446E12, 4110359.933333333], [1.64146464E12, 4041169.6666666665], [1.64146272E12, 3538990.3333333335], [1.64146434E12, 3708390.8666666667], [1.64146374E12, 3535230.3333333335], [1.64146404E12, 3563019.8666666667]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.64146524E12, "title": "Bytes Throughput Over Time"}},
        getOptions : function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity) ,
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Bytes / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendBytesThroughputOverTime'
                },
                selection: {
                    mode: "xy"
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y"
                }
            };
        },
        createGraph : function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesBytesThroughputOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotBytesThroughputOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewBytesThroughputOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Bytes throughput Over Time
function refreshBytesThroughputOverTime(fixTimestamps) {
    var infos = bytesThroughputOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotBytesThroughputOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesBytesThroughputOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotBytesThroughputOverTime", "#overviewBytesThroughputOverTime");
        $('#footerBytesThroughputOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimesOverTimeInfos = {
        data: {"result": {"minY": 0.6449469013628464, "minX": 1.6414626E12, "maxY": 2.9437918303075437, "series": [{"data": [[1.64146362E12, 2.5536425291233713], [1.64146392E12, 2.4544741147578253], [1.64146302E12, 2.737781772459277], [1.64146524E12, 0.6449469013628464], [1.64146332E12, 2.529983221550316], [1.64146494E12, 2.540289756673634], [1.6414629E12, 2.9437918303075437], [1.64146512E12, 0.9580506132142986], [1.6414632E12, 2.727970649791229], [1.64146482E12, 2.1116912667679895], [1.64146452E12, 2.584451311392119], [1.6414626E12, 1.9061631240588077], [1.64146422E12, 2.5308960093700956], [1.6414644E12, 2.494348832126656], [1.6414641E12, 2.4077665752433597], [1.6414635E12, 2.5195789566210935], [1.6414638E12, 2.513342286479835], [1.64146338E12, 2.479949647793192], [1.64146368E12, 2.3888708500077334], [1.641465E12, 2.1971500037898095], [1.64146278E12, 2.7785248065230816], [1.64146308E12, 2.881076692317069], [1.6414647E12, 2.538544380016653], [1.64146266E12, 1.8369157848676132], [1.64146488E12, 2.502352357684169], [1.64146296E12, 2.7739727750798724], [1.64146458E12, 2.5417008276378374], [1.64146428E12, 2.4564803550316956], [1.64146398E12, 2.22125585995248], [1.64146386E12, 2.5158918901898057], [1.64146416E12, 2.2358387287508097], [1.64146326E12, 2.603341367709525], [1.64146356E12, 2.6012802996854583], [1.64146518E12, 0.8737868771583319], [1.64146314E12, 2.735641819480829], [1.64146344E12, 2.480506814199672], [1.64146506E12, 1.8177202741690284], [1.64146476E12, 2.5223191771702997], [1.64146284E12, 2.74156397999838], [1.64146446E12, 2.19257335436256], [1.64146464E12, 2.2248012188550863], [1.64146272E12, 2.546356903489744], [1.64146434E12, 2.4248184877598566], [1.64146374E12, 2.539308584419471], [1.64146404E12, 2.522650804566516]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.64146524E12, "title": "Response Time Over Time"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average response time was %y ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Times Over Time
function refreshResponseTimeOverTime(fixTimestamps) {
    var infos = responseTimesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotResponseTimesOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesOverTime", "#overviewResponseTimesOverTime");
        $('#footerResponseTimesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var latenciesOverTimeInfos = {
        data: {"result": {"minY": 0.6420713864044593, "minX": 1.6414626E12, "maxY": 2.9338856705335647, "series": [{"data": [[1.64146362E12, 2.5457024111431656], [1.64146392E12, 2.4477376670663045], [1.64146302E12, 2.7302652904139504], [1.64146524E12, 0.6420713864044593], [1.64146332E12, 2.519555921559619], [1.64146494E12, 2.5332527078494533], [1.6414629E12, 2.9338856705335647], [1.64146512E12, 0.9523392231935929], [1.6414632E12, 2.704444122231497], [1.64146482E12, 2.1057552191973956], [1.64146452E12, 2.575680210146714], [1.6414626E12, 1.8983370496930259], [1.64146422E12, 2.523630711514793], [1.6414644E12, 2.486396782060602], [1.6414641E12, 2.398681792882372], [1.6414635E12, 2.5108284621807755], [1.6414638E12, 2.505329919946413], [1.64146338E12, 2.4728123949197633], [1.64146368E12, 2.38087791866571], [1.641465E12, 2.1906352236071522], [1.64146278E12, 2.769738829125534], [1.64146308E12, 2.871786942169247], [1.6414647E12, 2.5309377160733777], [1.64146266E12, 1.829040758118258], [1.64146488E12, 2.4915510716587566], [1.64146296E12, 2.7631735825757366], [1.64146458E12, 2.5314878752111287], [1.64146428E12, 2.446975720087149], [1.64146398E12, 2.212517585661023], [1.64146386E12, 2.508712791443117], [1.64146416E12, 2.229444255813043], [1.64146326E12, 2.595929633038191], [1.64146356E12, 2.5921952670070625], [1.64146518E12, 0.8688633448445993], [1.64146314E12, 2.725753750892999], [1.64146344E12, 2.473585589628874], [1.64146506E12, 1.812348564285757], [1.64146476E12, 2.513713390103002], [1.64146284E12, 2.73318329151143], [1.64146446E12, 2.1862546376512837], [1.64146464E12, 2.218466584220574], [1.64146272E12, 2.5394394691291557], [1.64146434E12, 2.41567380266986], [1.64146374E12, 2.5277031548439], [1.64146404E12, 2.5152620535487307]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.64146524E12, "title": "Latencies Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response latencies in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendLatenciesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average latency was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesLatenciesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotLatenciesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewLatenciesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Latencies Over Time
function refreshLatenciesOverTime(fixTimestamps) {
    var infos = latenciesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyLatenciesOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotLatenciesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesLatenciesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotLatenciesOverTime", "#overviewLatenciesOverTime");
        $('#footerLatenciesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var connectTimeOverTimeInfos = {
        data: {"result": {"minY": 0.01103232891320782, "minX": 1.6414626E12, "maxY": 0.0429378444843393, "series": [{"data": [[1.64146362E12, 0.036405262909588475], [1.64146392E12, 0.034474661256330634], [1.64146302E12, 0.03943929969832917], [1.64146524E12, 0.01103232891320782], [1.64146332E12, 0.034833118281466395], [1.64146494E12, 0.03718054804112058], [1.6414629E12, 0.04066788154709425], [1.64146512E12, 0.01770029278679893], [1.6414632E12, 0.0429378444843393], [1.64146482E12, 0.02737985416213727], [1.64146452E12, 0.03482981814583436], [1.6414626E12, 0.023575958531217234], [1.64146422E12, 0.033656382233924365], [1.6414644E12, 0.03301458063754989], [1.6414641E12, 0.031797576497252955], [1.6414635E12, 0.036155232277064735], [1.6414638E12, 0.034235292883297776], [1.64146338E12, 0.03310140279178972], [1.64146368E12, 0.03167858201661061], [1.641465E12, 0.030309921009534852], [1.64146278E12, 0.03745718450552687], [1.64146308E12, 0.037146912432368076], [1.6414647E12, 0.0338466925203052], [1.64146266E12, 0.02362444145631906], [1.64146488E12, 0.03389525634062577], [1.64146296E12, 0.03817992137947638], [1.64146458E12, 0.03449786906217443], [1.64146428E12, 0.03318311886362484], [1.64146398E12, 0.030097984439494935], [1.64146386E12, 0.034281770879342165], [1.64146416E12, 0.030302653589723845], [1.64146326E12, 0.03474557253788107], [1.64146356E12, 0.03473091676239157], [1.64146518E12, 0.015597434632461863], [1.64146314E12, 0.036143843772327405], [1.64146344E12, 0.035495119151398136], [1.64146506E12, 0.02460737791837721], [1.64146476E12, 0.03343118483935815], [1.64146284E12, 0.038785715175398874], [1.64146446E12, 0.028216588136911105], [1.64146464E12, 0.0313676840591278], [1.64146272E12, 0.03549468111008269], [1.64146434E12, 0.033181273969627306], [1.64146374E12, 0.033035678674780126], [1.64146404E12, 0.03377262486215282]], "isOverall": false, "label": "HTTP请求", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.64146524E12, "title": "Connect Time Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getConnectTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average Connect Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendConnectTimeOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average connect time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesConnectTimeOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotConnectTimeOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewConnectTimeOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Connect Time Over Time
function refreshConnectTimeOverTime(fixTimestamps) {
    var infos = connectTimeOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyConnectTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotConnectTimeOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesConnectTimeOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotConnectTimeOverTime", "#overviewConnectTimeOverTime");
        $('#footerConnectTimeOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var responseTimePercentilesOverTimeInfos = {
        data: {"result": {"minY": 0.0, "minX": 1.6414626E12, "maxY": 1521.0, "series": [{"data": [[1.64146362E12, 713.0], [1.64146392E12, 686.0], [1.64146302E12, 941.0], [1.64146524E12, 86.0], [1.64146332E12, 1128.0], [1.64146494E12, 692.0], [1.6414629E12, 736.0], [1.64146512E12, 258.0], [1.6414632E12, 574.0], [1.64146482E12, 695.0], [1.64146452E12, 1146.0], [1.6414626E12, 231.0], [1.64146422E12, 857.0], [1.6414644E12, 1010.0], [1.6414641E12, 793.0], [1.6414635E12, 1169.0], [1.6414638E12, 1001.0], [1.64146338E12, 736.0], [1.64146368E12, 680.0], [1.641465E12, 782.0], [1.64146278E12, 1347.0], [1.64146308E12, 632.0], [1.6414647E12, 799.0], [1.64146266E12, 937.0], [1.64146488E12, 758.0], [1.64146296E12, 1521.0], [1.64146458E12, 833.0], [1.64146428E12, 710.0], [1.64146398E12, 706.0], [1.64146386E12, 1254.0], [1.64146416E12, 684.0], [1.64146326E12, 522.0], [1.64146356E12, 542.0], [1.64146518E12, 222.0], [1.64146314E12, 1150.0], [1.64146344E12, 608.0], [1.64146506E12, 913.0], [1.64146476E12, 978.0], [1.64146284E12, 633.0], [1.64146446E12, 581.0], [1.64146464E12, 452.0], [1.64146272E12, 834.0], [1.64146434E12, 721.0], [1.64146374E12, 710.0], [1.64146404E12, 752.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.64146362E12, 4.0], [1.64146392E12, 3.0], [1.64146302E12, 4.0], [1.64146524E12, 1.0], [1.64146332E12, 3.0], [1.64146494E12, 4.0], [1.6414629E12, 4.0], [1.64146512E12, 1.0], [1.6414632E12, 4.0], [1.64146482E12, 3.0], [1.64146452E12, 4.0], [1.6414626E12, 2.0], [1.64146422E12, 3.0], [1.6414644E12, 3.0], [1.6414641E12, 2.0], [1.6414635E12, 4.0], [1.6414638E12, 3.0], [1.64146338E12, 4.0], [1.64146368E12, 3.0], [1.641465E12, 3.0], [1.64146278E12, 4.0], [1.64146308E12, 3.0], [1.6414647E12, 4.0], [1.64146266E12, 3.0], [1.64146488E12, 4.0], [1.64146296E12, 4.0], [1.64146458E12, 3.0], [1.64146428E12, 3.0], [1.64146398E12, 3.0], [1.64146386E12, 4.0], [1.64146416E12, 4.0], [1.64146326E12, 4.0], [1.64146356E12, 4.0], [1.64146518E12, 1.0], [1.64146314E12, 4.0], [1.64146344E12, 4.0], [1.64146506E12, 2.0], [1.64146476E12, 3.0], [1.64146284E12, 4.0], [1.64146446E12, 4.0], [1.64146464E12, 3.0], [1.64146272E12, 4.0], [1.64146434E12, 4.0], [1.64146374E12, 4.0], [1.64146404E12, 4.0]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.64146362E12, 9.0], [1.64146392E12, 7.0], [1.64146302E12, 34.0], [1.64146524E12, 1.0], [1.64146332E12, 9.0], [1.64146494E12, 8.0], [1.6414629E12, 34.0], [1.64146512E12, 3.0], [1.6414632E12, 9.0], [1.64146482E12, 5.0], [1.64146452E12, 8.0], [1.6414626E12, 4.0], [1.64146422E12, 12.0], [1.6414644E12, 7.0], [1.6414641E12, 7.0], [1.6414635E12, 8.0], [1.6414638E12, 12.0], [1.64146338E12, 9.0], [1.64146368E12, 8.0], [1.641465E12, 6.0], [1.64146278E12, 11.0], [1.64146308E12, 7.0], [1.6414647E12, 8.0], [1.64146266E12, 8.0], [1.64146488E12, 9.0], [1.64146296E12, 11.0], [1.64146458E12, 8.0], [1.64146428E12, 11.0], [1.64146398E12, 8.0], [1.64146386E12, 13.0], [1.64146416E12, 7.0], [1.64146326E12, 10.0], [1.64146356E12, 7.0], [1.64146518E12, 2.0], [1.64146314E12, 26.0], [1.64146344E12, 10.0], [1.64146506E12, 5.0], [1.64146476E12, 8.0], [1.64146284E12, 18.0], [1.64146446E12, 9.0], [1.64146464E12, 8.0], [1.64146272E12, 8.0], [1.64146434E12, 8.0], [1.64146374E12, 10.0], [1.64146404E12, 11.0]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.64146362E12, 4.0], [1.64146392E12, 4.0], [1.64146302E12, 10.0], [1.64146524E12, 1.0], [1.64146332E12, 4.0], [1.64146494E12, 4.0], [1.6414629E12, 9.0], [1.64146512E12, 2.0], [1.6414632E12, 4.0], [1.64146482E12, 3.0], [1.64146452E12, 4.0], [1.6414626E12, 3.0], [1.64146422E12, 4.0], [1.6414644E12, 4.0], [1.6414641E12, 3.0], [1.6414635E12, 4.0], [1.6414638E12, 4.0], [1.64146338E12, 4.0], [1.64146368E12, 4.0], [1.641465E12, 3.0], [1.64146278E12, 6.0], [1.64146308E12, 4.0], [1.6414647E12, 4.0], [1.64146266E12, 4.0], [1.64146488E12, 4.0], [1.64146296E12, 5.0], [1.64146458E12, 4.0], [1.64146428E12, 4.0], [1.64146398E12, 4.0], [1.64146386E12, 6.0], [1.64146416E12, 4.0], [1.64146326E12, 4.0], [1.64146356E12, 4.0], [1.64146518E12, 1.0], [1.64146314E12, 8.0], [1.64146344E12, 5.0], [1.64146506E12, 2.0], [1.64146476E12, 3.0], [1.64146284E12, 7.0], [1.64146446E12, 4.0], [1.64146464E12, 4.0], [1.64146272E12, 4.0], [1.64146434E12, 4.0], [1.64146374E12, 4.0], [1.64146404E12, 5.0]], "isOverall": false, "label": "95th percentile", "isController": false}, {"data": [[1.64146362E12, 0.0], [1.64146392E12, 0.0], [1.64146302E12, 0.0], [1.64146524E12, 0.0], [1.64146332E12, 0.0], [1.64146494E12, 0.0], [1.6414629E12, 0.0], [1.64146512E12, 0.0], [1.6414632E12, 0.0], [1.64146482E12, 0.0], [1.64146452E12, 0.0], [1.6414626E12, 0.0], [1.64146422E12, 0.0], [1.6414644E12, 0.0], [1.6414641E12, 0.0], [1.6414635E12, 0.0], [1.6414638E12, 0.0], [1.64146338E12, 0.0], [1.64146368E12, 0.0], [1.641465E12, 0.0], [1.64146278E12, 0.0], [1.64146308E12, 0.0], [1.6414647E12, 0.0], [1.64146266E12, 0.0], [1.64146488E12, 0.0], [1.64146296E12, 0.0], [1.64146458E12, 0.0], [1.64146428E12, 0.0], [1.64146398E12, 0.0], [1.64146386E12, 0.0], [1.64146416E12, 0.0], [1.64146326E12, 0.0], [1.64146356E12, 0.0], [1.64146518E12, 0.0], [1.64146314E12, 0.0], [1.64146344E12, 0.0], [1.64146506E12, 0.0], [1.64146476E12, 0.0], [1.64146284E12, 0.0], [1.64146446E12, 0.0], [1.64146464E12, 0.0], [1.64146272E12, 0.0], [1.64146434E12, 0.0], [1.64146374E12, 0.0], [1.64146404E12, 0.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.64146362E12, 2.0], [1.64146392E12, 2.0], [1.64146302E12, 2.0], [1.64146524E12, 0.0], [1.64146332E12, 2.0], [1.64146494E12, 2.0], [1.6414629E12, 2.0], [1.64146512E12, 1.0], [1.6414632E12, 2.0], [1.64146482E12, 2.0], [1.64146452E12, 2.0], [1.6414626E12, 1.0], [1.64146422E12, 2.0], [1.6414644E12, 2.0], [1.6414641E12, 2.0], [1.6414635E12, 2.0], [1.6414638E12, 2.0], [1.64146338E12, 2.0], [1.64146368E12, 2.0], [1.641465E12, 2.0], [1.64146278E12, 2.0], [1.64146308E12, 2.0], [1.6414647E12, 2.0], [1.64146266E12, 2.0], [1.64146488E12, 2.0], [1.64146296E12, 2.0], [1.64146458E12, 2.0], [1.64146428E12, 2.0], [1.64146398E12, 2.0], [1.64146386E12, 2.0], [1.64146416E12, 2.0], [1.64146326E12, 2.0], [1.64146356E12, 2.0], [1.64146518E12, 1.0], [1.64146314E12, 2.0], [1.64146344E12, 2.0], [1.64146506E12, 1.0], [1.64146476E12, 2.0], [1.64146284E12, 2.0], [1.64146446E12, 2.0], [1.64146464E12, 2.0], [1.64146272E12, 2.0], [1.64146434E12, 2.0], [1.64146374E12, 2.0], [1.64146404E12, 3.0]], "isOverall": false, "label": "Median", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.64146524E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Response Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentilesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Response time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentilesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimePercentilesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimePercentilesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Time Percentiles Over Time
function refreshResponseTimePercentilesOverTime(fixTimestamps) {
    var infos = responseTimePercentilesOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotResponseTimePercentilesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimePercentilesOverTime", "#overviewResponseTimePercentilesOverTime");
        $('#footerResponseTimePercentilesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var responseTimeVsRequestInfos = {
    data: {"result": {"minY": 0.0, "minX": 2100.0, "maxY": 4.0, "series": [{"data": [[16707.0, 1.0], [17091.0, 2.0], [16387.0, 2.0], [17987.0, 2.0], [17731.0, 2.0], [17923.0, 2.0], [18691.0, 2.0], [18499.0, 3.0], [19075.0, 2.0], [20227.0, 2.0], [19587.0, 2.0], [19779.0, 2.0], [20291.0, 2.0], [19907.0, 2.0], [21251.0, 2.0], [20995.0, 2.0], [21187.0, 2.0], [22467.0, 2.0], [23043.0, 2.0], [24963.0, 2.0], [26435.0, 1.0], [27267.0, 2.0], [27715.0, 1.0], [12449.0, 1.0], [14049.0, 3.0], [14433.0, 3.0], [14497.0, 2.0], [15137.0, 2.0], [14945.0, 2.0], [15617.0, 2.0], [15521.0, 3.0], [15361.0, 2.0], [15937.0, 2.0], [16225.0, 3.0], [16962.0, 2.0], [16770.0, 2.0], [16450.0, 2.0], [17154.0, 3.0], [16514.0, 2.0], [16706.0, 3.0], [17346.0, 1.0], [17666.0, 2.0], [17474.0, 2.0], [18370.0, 2.0], [18114.0, 1.0], [19074.0, 2.0], [18498.0, 2.0], [19202.0, 2.0], [18754.0, 2.0], [18562.0, 2.0], [19394.0, 2.0], [19650.0, 2.0], [20098.0, 2.0], [19714.0, 2.0], [19906.0, 2.0], [19778.0, 2.0], [20034.0, 2.0], [21250.0, 2.0], [21378.0, 2.0], [21634.0, 2.0], [24130.0, 2.0], [16961.0, 2.0], [16769.0, 1.0], [18177.0, 2.0], [18113.0, 2.0], [17537.0, 2.0], [19201.0, 2.0], [19265.0, 2.0], [18753.0, 2.0], [18817.0, 1.0], [18689.0, 2.0], [18433.0, 2.0], [18625.0, 1.0], [19009.0, 2.0], [19137.0, 2.0], [19905.0, 2.0], [19841.0, 2.0], [20289.0, 2.0], [19585.0, 2.0], [20225.0, 2.0], [19777.0, 2.0], [19649.0, 2.0], [19521.0, 2.0], [20865.0, 2.0], [20545.0, 2.0], [20481.0, 2.0], [20737.0, 2.0], [22081.0, 2.0], [22465.0, 2.0], [24385.0, 2.0], [25665.0, 2.0], [13408.0, 1.0], [14560.0, 2.0], [14784.0, 1.0], [15168.0, 2.0], [15552.0, 3.0], [15968.0, 2.0], [16352.0, 2.0], [16576.0, 3.0], [16768.0, 3.0], [16448.0, 2.0], [18368.0, 2.0], [18304.0, 2.0], [19392.0, 2.0], [19072.0, 2.0], [19008.0, 2.0], [18816.0, 2.0], [18688.0, 1.0], [18624.0, 1.0], [18560.0, 1.0], [19584.0, 2.0], [19776.0, 2.0], [20032.0, 2.0], [20096.0, 2.0], [19968.0, 2.0], [20416.0, 2.0], [19648.0, 2.0], [19840.0, 2.0], [19520.0, 2.0], [19456.0, 2.0], [19712.0, 2.0], [21312.0, 2.0], [20544.0, 2.0], [20608.0, 2.0], [20672.0, 2.0], [20480.0, 1.0], [21632.0, 2.0], [23168.0, 2.0], [23424.0, 2.0], [24768.0, 2.0], [25728.0, 2.0], [25600.0, 2.0], [16519.0, 1.0], [17159.0, 2.0], [17287.0, 2.0], [16583.0, 3.0], [17799.0, 2.0], [18183.0, 2.0], [19335.0, 2.0], [19271.0, 2.0], [19079.0, 2.0], [19143.0, 2.0], [18887.0, 2.0], [19911.0, 2.0], [19591.0, 2.0], [19847.0, 2.0], [19463.0, 2.0], [19975.0, 2.0], [19527.0, 2.0], [20231.0, 2.0], [19655.0, 2.0], [21127.0, 2.0], [20935.0, 2.0], [20807.0, 2.0], [20743.0, 2.0], [21255.0, 2.0], [23495.0, 2.0], [24199.0, 2.0], [23815.0, 2.0], [23623.0, 2.0], [14051.0, 2.0], [14243.0, 2.0], [14275.0, 2.0], [14307.0, 2.0], [14179.0, 1.0], [14467.0, 2.0], [14435.0, 2.0], [15107.0, 1.0], [15267.0, 1.0], [15683.0, 2.0], [16355.0, 2.0], [17222.0, 2.0], [17158.0, 2.0], [16838.0, 3.0], [16646.0, 3.0], [16774.0, 3.0], [16710.0, 2.0], [17542.0, 2.0], [18374.0, 2.0], [18118.0, 2.0], [17926.0, 2.0], [18054.0, 2.0], [19206.0, 2.0], [19398.0, 2.0], [19334.0, 2.0], [19014.0, 2.0], [18758.0, 2.0], [19142.0, 2.0], [18630.0, 2.0], [19526.0, 2.0], [19910.0, 2.0], [19718.0, 2.0], [19846.0, 2.0], [20038.0, 2.0], [19782.0, 2.0], [20998.0, 1.0], [20742.0, 2.0], [20486.0, 2.0], [22278.0, 2.0], [21510.0, 2.0], [27334.0, 2.0], [17285.0, 2.0], [16645.0, 1.0], [16517.0, 1.0], [17733.0, 3.0], [18373.0, 2.0], [17797.0, 2.0], [18181.0, 2.0], [17989.0, 2.0], [18117.0, 2.0], [19205.0, 2.0], [18757.0, 3.0], [19077.0, 2.0], [18629.0, 2.0], [18693.0, 2.0], [18437.0, 2.0], [18821.0, 2.0], [19909.0, 2.0], [19717.0, 2.0], [20293.0, 2.0], [20165.0, 2.0], [19973.0, 2.0], [20229.0, 2.0], [19781.0, 2.0], [20037.0, 2.0], [20485.0, 2.0], [20613.0, 2.0], [20805.0, 2.0], [21893.0, 2.0], [22277.0, 2.0], [21701.0, 2.0], [23877.0, 2.0], [24645.0, 2.0], [26245.0, 2.0], [25605.0, 2.0], [11522.0, 1.0], [12866.0, 1.0], [13954.0, 2.0], [14786.0, 3.0], [14818.0, 2.0], [14466.0, 2.0], [14530.0, 2.0], [15170.0, 2.0], [15554.0, 2.0], [16322.0, 3.0], [16388.0, 2.0], [16772.0, 3.0], [17156.0, 2.0], [17220.0, 3.0], [17284.0, 1.0], [18116.0, 1.0], [18692.0, 2.0], [19268.0, 2.0], [18436.0, 2.0], [19844.0, 2.0], [20292.0, 2.0], [19460.0, 2.0], [20420.0, 2.0], [20100.0, 2.0], [19716.0, 2.0], [20484.0, 2.0], [20804.0, 2.0], [21380.0, 2.0], [22404.0, 2.0], [24068.0, 2.0], [23556.0, 2.0], [24772.0, 2.0], [24580.0, 2.0], [30980.0, 1.0], [16523.0, 3.0], [17163.0, 3.0], [17035.0, 2.0], [16651.0, 3.0], [16907.0, 1.0], [16459.0, 1.0], [16843.0, 1.0], [18251.0, 2.0], [18123.0, 2.0], [17611.0, 2.0], [17995.0, 2.0], [18187.0, 2.0], [19275.0, 2.0], [19339.0, 2.0], [18443.0, 2.0], [18507.0, 2.0], [18571.0, 2.0], [18955.0, 2.0], [19147.0, 2.0], [18763.0, 2.0], [19467.0, 2.0], [19531.0, 2.0], [19979.0, 2.0], [21003.0, 2.0], [20683.0, 2.0], [20747.0, 2.0], [21131.0, 2.0], [22155.0, 2.0], [21579.0, 2.0], [23307.0, 2.0], [22603.0, 2.0], [22859.0, 2.0], [24011.0, 2.0], [28235.0, 1.0], [14213.0, 2.0], [13893.0, 2.0], [14661.0, 3.0], [15237.0, 3.0], [15781.0, 2.0], [15589.0, 2.0], [15621.0, 1.0], [16293.0, 2.0], [16197.0, 2.0], [15909.0, 1.0], [16650.0, 3.0], [17034.0, 3.0], [16842.0, 3.0], [17290.0, 2.0], [18378.0, 3.0], [18122.0, 2.0], [17610.0, 3.0], [17930.0, 3.0], [19338.0, 2.0], [19274.0, 2.0], [19146.0, 2.0], [18826.0, 2.0], [20234.0, 2.0], [19466.0, 2.0], [19978.0, 2.0], [19658.0, 2.0], [20426.0, 2.0], [19914.0, 2.0], [20106.0, 2.0], [19786.0, 2.0], [19530.0, 2.0], [20682.0, 2.0], [21514.0, 2.0], [22666.0, 2.0], [23434.0, 2.0], [24586.0, 2.0], [24714.0, 2.0], [26634.0, 2.0], [17289.0, 2.0], [16585.0, 2.0], [17097.0, 2.0], [17353.0, 2.0], [16713.0, 2.0], [16457.0, 3.0], [17737.0, 3.0], [18185.0, 3.0], [17609.0, 3.0], [18313.0, 2.0], [17673.0, 2.0], [17481.0, 2.0], [19337.0, 2.0], [19081.0, 2.0], [18441.0, 2.0], [18761.0, 2.0], [19401.0, 2.0], [19209.0, 2.0], [18505.0, 1.0], [20233.0, 2.0], [20425.0, 2.0], [19849.0, 2.0], [19465.0, 2.0], [20361.0, 2.0], [19977.0, 2.0], [20041.0, 2.0], [21065.0, 2.0], [21897.0, 2.0], [21833.0, 2.0], [24777.0, 2.0], [14884.0, 2.0], [15844.0, 3.0], [15396.0, 2.0], [15620.0, 3.0], [16132.0, 2.0], [16164.0, 2.0], [16068.0, 3.0], [16292.0, 3.0], [17352.0, 2.0], [16456.0, 2.0], [16904.0, 2.0], [16584.0, 3.0], [17032.0, 2.0], [16968.0, 1.0], [16392.0, 1.0], [17160.0, 1.0], [17736.0, 2.0], [17864.0, 2.0], [18376.0, 2.0], [17672.0, 2.0], [17800.0, 1.0], [18184.0, 1.0], [19080.0, 2.0], [18440.0, 2.0], [18696.0, 3.0], [19336.0, 2.0], [19144.0, 2.0], [19272.0, 2.0], [18568.0, 2.0], [18952.0, 2.0], [19656.0, 2.0], [19848.0, 2.0], [20424.0, 2.0], [20296.0, 2.0], [20040.0, 2.0], [20104.0, 2.0], [19592.0, 2.0], [20616.0, 2.0], [20744.0, 2.0], [21320.0, 2.0], [21256.0, 2.0], [20872.0, 2.0], [21576.0, 2.0], [21640.0, 2.0], [23240.0, 2.0], [27720.0, 1.0], [30920.0, 1.0], [16783.0, 2.0], [16719.0, 3.0], [16975.0, 2.0], [16911.0, 2.0], [17743.0, 2.0], [17935.0, 2.0], [18127.0, 2.0], [19215.0, 2.0], [19407.0, 2.0], [18575.0, 2.0], [19023.0, 2.0], [19343.0, 2.0], [19087.0, 2.0], [19663.0, 2.0], [19599.0, 2.0], [20111.0, 2.0], [20175.0, 2.0], [19535.0, 2.0], [19855.0, 2.0], [20303.0, 2.0], [20367.0, 2.0], [19471.0, 2.0], [20047.0, 2.0], [21135.0, 2.0], [21007.0, 2.0], [21583.0, 2.0], [22031.0, 2.0], [21903.0, 2.0], [22863.0, 2.0], [23951.0, 2.0], [24783.0, 2.0], [25167.0, 2.0], [8551.0, 1.0], [14631.0, 2.0], [14567.0, 2.0], [15847.0, 2.0], [15687.0, 2.0], [15559.0, 3.0], [16135.0, 3.0], [16231.0, 3.0], [16007.0, 2.0], [16398.0, 2.0], [17038.0, 2.0], [16846.0, 1.0], [17550.0, 2.0], [18062.0, 2.0], [17806.0, 2.0], [17614.0, 2.0], [17870.0, 2.0], [18958.0, 2.0], [18638.0, 2.0], [19086.0, 2.0], [19022.0, 2.0], [18446.0, 2.0], [18766.0, 2.0], [18510.0, 2.0], [19342.0, 2.0], [19278.0, 2.0], [20302.0, 2.0], [19726.0, 2.0], [20174.0, 2.0], [19982.0, 2.0], [19534.0, 2.0], [20878.0, 2.0], [21134.0, 2.0], [20558.0, 2.0], [24526.0, 2.0], [24014.0, 2.0], [25422.0, 2.0], [26382.0, 2.0], [25678.0, 2.0], [26766.0, 2.0], [26702.0, 2.0], [28046.0, 2.0], [17037.0, 2.0], [16589.0, 2.0], [16781.0, 2.0], [16717.0, 3.0], [17165.0, 2.0], [16909.0, 2.0], [17485.0, 3.0], [18253.0, 2.0], [17677.0, 3.0], [18061.0, 3.0], [18445.0, 2.0], [19405.0, 2.0], [19341.0, 2.0], [18765.0, 2.0], [19021.0, 2.0], [19661.0, 2.0], [19469.0, 2.0], [19981.0, 2.0], [20429.0, 2.0], [20237.0, 2.0], [19917.0, 2.0], [20109.0, 2.0], [19725.0, 2.0], [20749.0, 2.0], [21197.0, 2.0], [21517.0, 2.0], [22477.0, 2.0], [23309.0, 2.0], [24333.0, 2.0], [25165.0, 2.0], [13254.0, 2.0], [13446.0, 3.0], [14182.0, 1.0], [15334.0, 2.0], [14982.0, 2.0], [15558.0, 2.0], [15718.0, 3.0], [16166.0, 2.0], [16326.0, 2.0], [15878.0, 2.0], [16262.0, 3.0], [16396.0, 2.0], [16652.0, 3.0], [17100.0, 1.0], [16908.0, 2.0], [16460.0, 1.0], [16844.0, 1.0], [17804.0, 2.0], [17932.0, 2.0], [17484.0, 2.0], [17548.0, 1.0], [17676.0, 1.0], [18572.0, 2.0], [19020.0, 2.0], [19276.0, 2.0], [18956.0, 3.0], [19468.0, 2.0], [19788.0, 2.0], [19724.0, 2.0], [20044.0, 2.0], [19660.0, 2.0], [19532.0, 2.0], [19852.0, 2.0], [19596.0, 2.0], [20684.0, 2.0], [21004.0, 2.0], [22412.0, 2.0], [23436.0, 2.0], [23372.0, 2.0], [31372.0, 1.0], [17363.0, 1.0], [19411.0, 2.0], [19027.0, 2.0], [19091.0, 2.0], [19155.0, 2.0], [18451.0, 2.0], [18515.0, 2.0], [18963.0, 2.0], [19283.0, 2.0], [19219.0, 2.0], [18643.0, 2.0], [18771.0, 2.0], [18899.0, 2.0], [20243.0, 2.0], [19539.0, 2.0], [20179.0, 2.0], [20435.0, 2.0], [20051.0, 2.0], [20947.0, 2.0], [21331.0, 2.0], [21523.0, 2.0], [23571.0, 2.0], [26643.0, 2.0], [29715.0, 1.0], [13097.0, 2.0], [13929.0, 2.0], [14825.0, 2.0], [15273.0, 1.0], [15561.0, 2.0], [15817.0, 2.0], [16073.0, 2.0], [16169.0, 2.0], [15977.0, 2.0], [17106.0, 2.0], [16850.0, 2.0], [16594.0, 3.0], [16402.0, 3.0], [16978.0, 2.0], [18066.0, 2.0], [17554.0, 2.0], [18450.0, 2.0], [19090.0, 2.0], [19154.0, 2.0], [19282.0, 2.0], [19346.0, 2.0], [19922.0, 2.0], [19538.0, 2.0], [20114.0, 2.0], [20306.0, 2.0], [20370.0, 2.0], [19858.0, 2.0], [19474.0, 2.0], [20882.0, 2.0], [21138.0, 2.0], [21394.0, 2.0], [21074.0, 2.0], [20626.0, 2.0], [20498.0, 2.0], [21202.0, 2.0], [23506.0, 2.0], [23762.0, 2.0], [26514.0, 2.0], [26706.0, 1.0], [31186.0, 1.0], [17233.0, 2.0], [16529.0, 2.0], [16401.0, 2.0], [16721.0, 3.0], [17361.0, 2.0], [16913.0, 3.0], [17297.0, 1.0], [17425.0, 2.0], [17617.0, 3.0], [17681.0, 3.0], [17553.0, 2.0], [18769.0, 2.0], [19409.0, 2.0], [19217.0, 2.0], [18897.0, 2.0], [18833.0, 2.0], [19281.0, 2.0], [18641.0, 2.0], [19601.0, 2.0], [19857.0, 2.0], [19729.0, 2.0], [19473.0, 2.0], [19985.0, 2.0], [20433.0, 2.0], [19921.0, 2.0], [20369.0, 2.0], [20305.0, 2.0], [20881.0, 2.0], [20625.0, 2.0], [21009.0, 2.0], [21521.0, 2.0], [22929.0, 2.0], [24337.0, 2.0], [27665.0, 2.0], [14536.0, 2.0], [15144.0, 1.0], [15560.0, 3.0], [15400.0, 2.0], [15496.0, 3.0], [15592.0, 1.0], [16264.0, 2.0], [15912.0, 1.0], [17168.0, 2.0], [16976.0, 1.0], [18000.0, 2.0], [19280.0, 2.0], [18960.0, 2.0], [18640.0, 2.0], [19216.0, 2.0], [19024.0, 2.0], [18704.0, 2.0], [18896.0, 2.0], [18832.0, 2.0], [19920.0, 2.0], [19664.0, 3.0], [19984.0, 2.0], [19856.0, 2.0], [20112.0, 2.0], [20176.0, 2.0], [21072.0, 2.0], [21328.0, 2.0], [21904.0, 2.0], [23056.0, 2.0], [24272.0, 2.0], [26384.0, 2.0], [26896.0, 2.0], [17111.0, 3.0], [16983.0, 3.0], [16663.0, 2.0], [16919.0, 2.0], [17751.0, 2.0], [17815.0, 2.0], [17431.0, 2.0], [17495.0, 3.0], [17943.0, 3.0], [18071.0, 3.0], [19223.0, 2.0], [18519.0, 2.0], [18967.0, 2.0], [18839.0, 2.0], [18711.0, 2.0], [19287.0, 2.0], [19735.0, 2.0], [20439.0, 2.0], [19543.0, 2.0], [20375.0, 2.0], [19607.0, 2.0], [20247.0, 2.0], [19799.0, 2.0], [19671.0, 2.0], [20823.0, 2.0], [20503.0, 2.0], [20695.0, 2.0], [21207.0, 2.0], [21975.0, 2.0], [22935.0, 2.0], [24279.0, 2.0], [24407.0, 2.0], [24727.0, 2.0], [24983.0, 2.0], [25431.0, 2.0], [24663.0, 2.0], [26519.0, 2.0], [26583.0, 2.0], [26199.0, 2.0], [27735.0, 2.0], [13259.0, 2.0], [15659.0, 2.0], [15819.0, 3.0], [15755.0, 3.0], [16043.0, 3.0], [16171.0, 3.0], [16235.0, 3.0], [17046.0, 2.0], [17174.0, 2.0], [16470.0, 2.0], [17494.0, 2.0], [17686.0, 3.0], [17942.0, 2.0], [18966.0, 2.0], [19094.0, 2.0], [19158.0, 2.0], [19030.0, 2.0], [19350.0, 2.0], [19286.0, 2.0], [18902.0, 2.0], [19990.0, 2.0], [20310.0, 2.0], [20758.0, 2.0], [22486.0, 2.0], [22614.0, 2.0], [23446.0, 2.0], [27862.0, 2.0], [16917.0, 2.0], [16725.0, 3.0], [16469.0, 2.0], [16533.0, 3.0], [17109.0, 2.0], [17045.0, 2.0], [16661.0, 1.0], [17493.0, 2.0], [17941.0, 2.0], [17877.0, 2.0], [17685.0, 2.0], [19157.0, 2.0], [19413.0, 2.0], [18901.0, 2.0], [18645.0, 2.0], [18453.0, 3.0], [18837.0, 2.0], [19861.0, 2.0], [19989.0, 2.0], [19541.0, 2.0], [20117.0, 2.0], [20821.0, 2.0], [20885.0, 2.0], [21333.0, 2.0], [21717.0, 2.0], [21589.0, 2.0], [23509.0, 2.0], [24853.0, 2.0], [26197.0, 1.0], [12202.0, 1.0], [13130.0, 2.0], [13482.0, 2.0], [13930.0, 3.0], [14250.0, 1.0], [13834.0, 1.0], [15178.0, 2.0], [15338.0, 2.0], [15786.0, 2.0], [15466.0, 2.0], [16234.0, 3.0], [16330.0, 3.0], [16266.0, 2.0], [16788.0, 2.0], [16532.0, 2.0], [17108.0, 2.0], [16404.0, 3.0], [17556.0, 3.0], [18132.0, 2.0], [19348.0, 2.0], [19220.0, 2.0], [19092.0, 2.0], [18644.0, 2.0], [18452.0, 2.0], [18708.0, 2.0], [18836.0, 2.0], [19604.0, 2.0], [19924.0, 2.0], [20180.0, 2.0], [20308.0, 2.0], [19860.0, 2.0], [20820.0, 2.0], [20500.0, 2.0], [21076.0, 2.0], [20628.0, 2.0], [20884.0, 2.0], [22228.0, 2.0], [22932.0, 2.0], [24020.0, 1.0], [24340.0, 2.0], [23828.0, 2.0], [28180.0, 2.0], [16411.0, 2.0], [16923.0, 2.0], [17563.0, 3.0], [18331.0, 2.0], [18011.0, 3.0], [18843.0, 2.0], [19419.0, 2.0], [19163.0, 2.0], [19035.0, 2.0], [20251.0, 2.0], [19675.0, 2.0], [20443.0, 2.0], [19803.0, 2.0], [19739.0, 2.0], [20315.0, 2.0], [20507.0, 2.0], [21531.0, 2.0], [21787.0, 2.0], [22491.0, 2.0], [24859.0, 2.0], [25691.0, 2.0], [26523.0, 2.0], [27035.0, 2.0], [27163.0, 2.0], [16269.0, 3.0], [15949.0, 2.0], [16205.0, 1.0], [16474.0, 2.0], [16730.0, 2.0], [16410.0, 2.0], [16922.0, 2.0], [16986.0, 3.0], [16666.0, 1.0], [17754.0, 2.0], [18010.0, 2.0], [17626.0, 2.0], [18330.0, 2.0], [18906.0, 1.0], [19098.0, 2.0], [18778.0, 2.0], [19290.0, 2.0], [19354.0, 2.0], [18458.0, 2.0], [19930.0, 2.0], [19546.0, 2.0], [19674.0, 2.0], [19802.0, 2.0], [19866.0, 2.0], [20250.0, 2.0], [20378.0, 2.0], [19994.0, 2.0], [20058.0, 2.0], [20954.0, 2.0], [20634.0, 2.0], [20506.0, 2.0], [22234.0, 2.0], [21530.0, 2.0], [22426.0, 2.0], [21978.0, 2.0], [24602.0, 2.0], [24666.0, 2.0], [16985.0, 2.0], [16601.0, 3.0], [17049.0, 1.0], [18329.0, 2.0], [18137.0, 2.0], [17945.0, 2.0], [17497.0, 2.0], [17881.0, 2.0], [18265.0, 2.0], [19353.0, 2.0], [19033.0, 2.0], [18777.0, 2.0], [18969.0, 2.0], [19225.0, 2.0], [19545.0, 2.0], [19481.0, 2.0], [20057.0, 2.0], [20185.0, 2.0], [20377.0, 2.0], [19865.0, 2.0], [20313.0, 2.0], [19993.0, 2.0], [20761.0, 2.0], [20633.0, 2.0], [21657.0, 2.0], [22617.0, 2.0], [23449.0, 2.0], [23769.0, 2.0], [24857.0, 2.0], [14060.0, 2.0], [14668.0, 2.0], [14828.0, 2.0], [14604.0, 1.0], [15084.0, 1.0], [15692.0, 2.0], [15468.0, 3.0], [16364.0, 2.0], [16268.0, 2.0], [16076.0, 3.0], [16300.0, 1.0], [17176.0, 3.0], [17112.0, 2.0], [17560.0, 2.0], [18136.0, 2.0], [17752.0, 2.0], [17688.0, 2.0], [18392.0, 2.0], [17816.0, 2.0], [18072.0, 2.0], [17496.0, 1.0], [17624.0, 1.0], [19032.0, 2.0], [18968.0, 2.0], [18904.0, 2.0], [18712.0, 3.0], [18520.0, 2.0], [19608.0, 2.0], [21272.0, 2.0], [20824.0, 2.0], [21656.0, 2.0], [22104.0, 2.0], [22040.0, 2.0], [22872.0, 2.0], [23320.0, 2.0], [24280.0, 2.0], [24408.0, 2.0], [24920.0, 2.0], [25688.0, 2.0], [26840.0, 2.0], [17375.0, 2.0], [17119.0, 3.0], [17055.0, 2.0], [16415.0, 1.0], [17247.0, 1.0], [17695.0, 2.0], [18399.0, 2.0], [17887.0, 2.0], [18079.0, 2.0], [18207.0, 2.0], [19039.0, 2.0], [18655.0, 2.0], [18847.0, 2.0], [18719.0, 2.0], [19295.0, 2.0], [19423.0, 2.0], [19551.0, 2.0], [20127.0, 2.0], [19935.0, 2.0], [19487.0, 2.0], [19743.0, 2.0], [20447.0, 2.0], [20383.0, 2.0], [19871.0, 2.0], [20063.0, 2.0], [20319.0, 2.0], [19679.0, 2.0], [21343.0, 2.0], [21727.0, 2.0], [22047.0, 2.0], [23519.0, 2.0], [23583.0, 2.0], [24351.0, 2.0], [25759.0, 2.0], [29535.0, 1.0], [9775.0, 1.0], [13967.0, 2.0], [13903.0, 2.0], [14735.0, 1.0], [15183.0, 2.0], [14927.0, 2.0], [15023.0, 2.0], [15503.0, 2.0], [16239.0, 2.0], [16175.0, 2.0], [16207.0, 2.0], [16271.0, 2.0], [15951.0, 3.0], [17182.0, 2.0], [16670.0, 2.0], [16414.0, 2.0], [16478.0, 2.0], [17822.0, 2.0], [17630.0, 3.0], [18078.0, 2.0], [17694.0, 3.0], [18334.0, 2.0], [18014.0, 3.0], [18526.0, 2.0], [19166.0, 2.0], [18654.0, 2.0], [19550.0, 2.0], [19742.0, 2.0], [19486.0, 2.0], [19614.0, 2.0], [19998.0, 2.0], [20190.0, 2.0], [20126.0, 2.0], [20638.0, 2.0], [24542.0, 2.0], [23646.0, 2.0], [26462.0, 2.0], [26910.0, 2.0], [16605.0, 3.0], [17053.0, 3.0], [17757.0, 2.0], [17693.0, 2.0], [17629.0, 2.0], [18205.0, 2.0], [17949.0, 2.0], [18269.0, 2.0], [19293.0, 2.0], [18461.0, 2.0], [18589.0, 2.0], [19037.0, 2.0], [18525.0, 2.0], [19229.0, 2.0], [20061.0, 2.0], [20317.0, 2.0], [19933.0, 2.0], [19997.0, 2.0], [20573.0, 2.0], [21597.0, 2.0], [22429.0, 2.0], [27805.0, 2.0], [13230.0, 2.0], [14350.0, 3.0], [14510.0, 2.0], [14734.0, 2.0], [14702.0, 2.0], [15182.0, 2.0], [15310.0, 3.0], [14990.0, 2.0], [15598.0, 2.0], [15438.0, 1.0], [16238.0, 2.0], [16206.0, 2.0], [16334.0, 3.0], [15950.0, 2.0], [16110.0, 2.0], [16476.0, 3.0], [16796.0, 2.0], [17308.0, 3.0], [16412.0, 3.0], [17372.0, 2.0], [16668.0, 3.0], [16988.0, 2.0], [17180.0, 1.0], [16604.0, 1.0], [18012.0, 2.0], [17628.0, 2.0], [18076.0, 2.0], [18268.0, 2.0], [18652.0, 2.0], [19228.0, 2.0], [18972.0, 2.0], [19420.0, 2.0], [18716.0, 2.0], [18844.0, 2.0], [19356.0, 2.0], [18524.0, 2.0], [18908.0, 2.0], [19676.0, 2.0], [19932.0, 2.0], [20252.0, 2.0], [19548.0, 2.0], [20124.0, 2.0], [20188.0, 2.0], [19740.0, 2.0], [20316.0, 2.0], [20380.0, 2.0], [19804.0, 2.0], [21724.0, 2.0], [22428.0, 2.0], [21980.0, 2.0], [21660.0, 2.0], [22620.0, 2.0], [22556.0, 2.0], [16547.0, 2.0], [16611.0, 1.0], [17379.0, 2.0], [16675.0, 1.0], [18083.0, 2.0], [17699.0, 2.0], [17507.0, 2.0], [17955.0, 2.0], [17827.0, 2.0], [17763.0, 2.0], [18979.0, 2.0], [19363.0, 2.0], [19235.0, 2.0], [19427.0, 2.0], [18467.0, 1.0], [19811.0, 2.0], [20387.0, 2.0], [20323.0, 2.0], [20643.0, 2.0], [22051.0, 2.0], [22179.0, 2.0], [23395.0, 2.0], [22755.0, 2.0], [24483.0, 2.0], [24099.0, 2.0], [25571.0, 2.0], [25059.0, 2.0], [13713.0, 3.0], [14737.0, 2.0], [15569.0, 2.0], [16369.0, 2.0], [16081.0, 2.0], [15953.0, 2.0], [16177.0, 3.0], [16017.0, 3.0], [16482.0, 2.0], [17122.0, 2.0], [16738.0, 2.0], [16802.0, 3.0], [16610.0, 1.0], [17186.0, 2.0], [17314.0, 1.0], [17826.0, 2.0], [17698.0, 3.0], [18082.0, 2.0], [18402.0, 2.0], [18338.0, 2.0], [17570.0, 2.0], [19042.0, 2.0], [18594.0, 2.0], [18658.0, 2.0], [18722.0, 2.0], [19810.0, 2.0], [19746.0, 1.0], [20066.0, 2.0], [20386.0, 2.0], [19618.0, 2.0], [19682.0, 2.0], [20450.0, 2.0], [21410.0, 2.0], [20770.0, 2.0], [20834.0, 2.0], [20642.0, 2.0], [22434.0, 2.0], [23266.0, 2.0], [23778.0, 2.0], [24290.0, 2.0], [24738.0, 2.0], [27362.0, 2.0], [16737.0, 2.0], [16865.0, 2.0], [17057.0, 1.0], [16929.0, 2.0], [16993.0, 2.0], [17761.0, 2.0], [17569.0, 2.0], [18849.0, 2.0], [19233.0, 2.0], [19425.0, 2.0], [18529.0, 2.0], [19489.0, 2.0], [20001.0, 2.0], [19809.0, 2.0], [19937.0, 2.0], [20833.0, 2.0], [21409.0, 2.0], [20769.0, 2.0], [22497.0, 2.0], [22177.0, 1.0], [25057.0, 2.0], [27041.0, 2.0], [26721.0, 2.0], [29089.0, 1.0], [2100.0, 0.0], [13264.0, 4.0], [14128.0, 2.0], [14384.0, 2.0], [15216.0, 3.0], [15312.0, 2.0], [15408.0, 2.0], [15440.0, 2.0], [15824.0, 3.0], [15568.0, 3.0], [15696.0, 3.0], [15888.0, 3.0], [16240.0, 3.0], [16928.0, 2.0], [17120.0, 2.0], [17056.0, 1.0], [16480.0, 1.0], [18144.0, 2.0], [18400.0, 2.0], [18592.0, 2.0], [18656.0, 2.0], [19232.0, 2.0], [18848.0, 2.0], [19040.0, 2.0], [19296.0, 2.0], [18912.0, 2.0], [20256.0, 2.0], [20128.0, 2.0], [19616.0, 2.0], [19680.0, 2.0], [19808.0, 2.0], [19552.0, 2.0], [20064.0, 2.0], [20576.0, 2.0], [20896.0, 2.0], [21728.0, 2.0], [22624.0, 2.0], [23520.0, 2.0], [23456.0, 2.0], [25120.0, 2.0], [25568.0, 2.0], [25824.0, 2.0], [16551.0, 2.0], [16807.0, 3.0], [17191.0, 1.0], [16935.0, 1.0], [18215.0, 2.0], [18151.0, 2.0], [18343.0, 2.0], [18023.0, 2.0], [19367.0, 2.0], [18791.0, 2.0], [19047.0, 2.0], [18983.0, 2.0], [19239.0, 2.0], [19495.0, 2.0], [20135.0, 2.0], [20199.0, 2.0], [19815.0, 2.0], [20839.0, 2.0], [20519.0, 2.0], [20967.0, 2.0], [21735.0, 2.0], [22311.0, 2.0], [23015.0, 2.0], [23335.0, 2.0], [24231.0, 2.0], [24295.0, 2.0], [24871.0, 2.0], [25383.0, 2.0], [27623.0, 2.0], [13651.0, 3.0], [13843.0, 2.0], [13907.0, 2.0], [15539.0, 2.0], [16147.0, 2.0], [16243.0, 2.0], [17318.0, 2.0], [16870.0, 3.0], [16806.0, 3.0], [16614.0, 2.0], [17062.0, 1.0], [17254.0, 1.0], [18214.0, 3.0], [17446.0, 2.0], [18086.0, 2.0], [18918.0, 2.0], [19238.0, 2.0], [19430.0, 2.0], [18726.0, 2.0], [18790.0, 1.0], [19110.0, 2.0], [19174.0, 2.0], [18470.0, 2.0], [20262.0, 2.0], [19622.0, 2.0], [20326.0, 2.0], [20454.0, 2.0], [19942.0, 2.0], [19494.0, 2.0], [19750.0, 2.0], [20070.0, 2.0], [20646.0, 2.0], [20966.0, 2.0], [20902.0, 2.0], [20774.0, 2.0], [21350.0, 2.0], [21990.0, 2.0], [21606.0, 2.0], [22502.0, 2.0], [23078.0, 2.0], [23014.0, 2.0], [24230.0, 2.0], [24486.0, 2.0], [24806.0, 2.0], [27110.0, 2.0], [27430.0, 2.0], [16485.0, 2.0], [16741.0, 2.0], [16421.0, 3.0], [17125.0, 1.0], [17381.0, 1.0], [17445.0, 2.0], [18277.0, 2.0], [17893.0, 2.0], [18021.0, 2.0], [18725.0, 2.0], [18853.0, 2.0], [19429.0, 2.0], [18533.0, 1.0], [19941.0, 2.0], [19813.0, 2.0], [19749.0, 2.0], [20069.0, 2.0], [19877.0, 2.0], [19621.0, 2.0], [19557.0, 2.0], [19685.0, 2.0], [20389.0, 2.0], [21093.0, 2.0], [21029.0, 2.0], [21349.0, 2.0], [20837.0, 2.0], [21797.0, 2.0], [21541.0, 2.0], [23077.0, 2.0], [23333.0, 2.0], [25765.0, 2.0], [14482.0, 1.0], [14930.0, 3.0], [15058.0, 1.0], [15538.0, 3.0], [15602.0, 2.0], [15634.0, 2.0], [15762.0, 3.0], [16114.0, 3.0], [15922.0, 3.0], [16178.0, 1.0], [16740.0, 2.0], [16996.0, 2.0], [16420.0, 2.0], [16612.0, 2.0], [17124.0, 2.0], [16932.0, 2.0], [16804.0, 2.0], [17636.0, 2.0], [17828.0, 2.0], [18212.0, 2.0], [17764.0, 2.0], [19236.0, 2.0], [18980.0, 2.0], [19940.0, 2.0], [19748.0, 2.0], [19812.0, 2.0], [19684.0, 2.0], [19556.0, 2.0], [20388.0, 2.0], [20964.0, 2.0], [20836.0, 2.0], [20900.0, 2.0], [21348.0, 2.0], [21092.0, 2.0], [21156.0, 2.0], [21668.0, 2.0], [21796.0, 2.0], [22564.0, 2.0], [25700.0, 2.0], [29668.0, 1.0], [16491.0, 2.0], [17259.0, 2.0], [16811.0, 2.0], [16619.0, 2.0], [17067.0, 2.0], [17003.0, 1.0], [18347.0, 2.0], [17835.0, 2.0], [17707.0, 3.0], [17515.0, 2.0], [18603.0, 2.0], [18539.0, 2.0], [18731.0, 2.0], [18667.0, 2.0], [19435.0, 2.0], [19115.0, 1.0], [19499.0, 2.0], [20011.0, 2.0], [20395.0, 2.0], [20075.0, 2.0], [19947.0, 2.0], [20139.0, 2.0], [19563.0, 2.0], [20587.0, 2.0], [20843.0, 2.0], [21227.0, 2.0], [21163.0, 2.0], [21867.0, 2.0], [22635.0, 2.0], [25515.0, 2.0], [26155.0, 2.0], [26795.0, 2.0], [27243.0, 2.0], [14293.0, 2.0], [15061.0, 2.0], [15669.0, 2.0], [15797.0, 2.0], [15509.0, 2.0], [15637.0, 2.0], [15413.0, 3.0], [15541.0, 3.0], [16309.0, 2.0], [16245.0, 2.0], [16053.0, 1.0], [17194.0, 2.0], [17386.0, 2.0], [16746.0, 2.0], [16490.0, 3.0], [17130.0, 2.0], [17898.0, 2.0], [18026.0, 2.0], [18538.0, 2.0], [19114.0, 2.0], [19306.0, 2.0], [18474.0, 2.0], [18858.0, 2.0], [18794.0, 2.0], [18986.0, 2.0], [19626.0, 2.0], [20010.0, 2.0], [19946.0, 2.0], [20458.0, 2.0], [20330.0, 2.0], [19818.0, 2.0], [19882.0, 2.0], [20138.0, 2.0], [20778.0, 2.0], [21290.0, 2.0], [20714.0, 2.0], [20522.0, 2.0], [20906.0, 2.0], [22058.0, 2.0], [22122.0, 2.0], [23402.0, 2.0], [24554.0, 2.0], [23978.0, 2.0], [17257.0, 3.0], [16937.0, 1.0], [18409.0, 2.0], [19241.0, 2.0], [19113.0, 2.0], [18793.0, 2.0], [19177.0, 2.0], [18537.0, 2.0], [19049.0, 2.0], [19753.0, 2.0], [20137.0, 2.0], [20201.0, 2.0], [20265.0, 2.0], [20457.0, 2.0], [20393.0, 2.0], [19881.0, 2.0], [20585.0, 2.0], [21161.0, 2.0], [20713.0, 2.0], [22505.0, 2.0], [22185.0, 2.0], [22377.0, 2.0], [23209.0, 2.0], [22569.0, 2.0], [24233.0, 2.0], [24489.0, 2.0], [24617.0, 2.0], [25065.0, 2.0], [25257.0, 2.0], [25001.0, 2.0], [24681.0, 2.0], [27369.0, 2.0], [27817.0, 1.0], [32489.0, 1.0], [2861.0, 0.0], [13268.0, 3.0], [13652.0, 2.0], [13844.0, 2.0], [14804.0, 3.0], [14612.0, 2.0], [14452.0, 3.0], [14836.0, 2.0], [15252.0, 2.0], [15348.0, 2.0], [16180.0, 2.0], [16084.0, 3.0], [16020.0, 2.0], [17192.0, 2.0], [16744.0, 2.0], [16424.0, 2.0], [18088.0, 2.0], [18408.0, 2.0], [17896.0, 2.0], [18344.0, 3.0], [17640.0, 2.0], [19240.0, 2.0], [18664.0, 2.0], [19560.0, 2.0], [20136.0, 2.0], [19816.0, 2.0], [19624.0, 2.0], [19688.0, 2.0], [19496.0, 2.0], [20712.0, 2.0], [21480.0, 2.0], [20840.0, 2.0], [21608.0, 2.0], [21736.0, 2.0], [22120.0, 2.0], [24296.0, 2.0], [24040.0, 2.0], [24680.0, 2.0], [25384.0, 2.0], [26088.0, 2.0], [17135.0, 2.0], [16687.0, 2.0], [17391.0, 2.0], [16431.0, 3.0], [17519.0, 3.0], [18031.0, 2.0], [17711.0, 2.0], [18735.0, 2.0], [18927.0, 2.0], [18479.0, 2.0], [19311.0, 2.0], [19375.0, 2.0], [19183.0, 2.0], [19695.0, 2.0], [20143.0, 2.0], [20079.0, 2.0], [19503.0, 2.0], [19951.0, 2.0], [19759.0, 2.0], [19823.0, 2.0], [20399.0, 2.0], [19631.0, 2.0], [20975.0, 2.0], [21039.0, 2.0], [21167.0, 2.0], [21295.0, 2.0], [20847.0, 2.0], [22127.0, 2.0], [22319.0, 2.0], [22767.0, 2.0], [24175.0, 2.0], [25007.0, 2.0], [25839.0, 2.0], [31087.0, 1.0], [13879.0, 2.0], [14743.0, 2.0], [14615.0, 2.0], [14839.0, 2.0], [15287.0, 2.0], [15863.0, 2.0], [16055.0, 3.0], [16087.0, 1.0], [16942.0, 3.0], [16622.0, 2.0], [17070.0, 2.0], [17006.0, 1.0], [16814.0, 2.0], [16494.0, 2.0], [16878.0, 2.0], [16558.0, 1.0], [17198.0, 1.0], [16750.0, 1.0], [17966.0, 2.0], [18414.0, 2.0], [17838.0, 2.0], [17582.0, 3.0], [18542.0, 2.0], [19054.0, 2.0], [19118.0, 2.0], [18862.0, 2.0], [18798.0, 2.0], [18926.0, 2.0], [18670.0, 2.0], [19374.0, 2.0], [19438.0, 2.0], [19630.0, 2.0], [20334.0, 2.0], [20142.0, 2.0], [19822.0, 2.0], [19886.0, 2.0], [19566.0, 2.0], [21358.0, 2.0], [21038.0, 2.0], [20526.0, 2.0], [20718.0, 2.0], [20846.0, 2.0], [23662.0, 2.0], [25518.0, 2.0], [24942.0, 2.0], [27182.0, 2.0], [17197.0, 3.0], [17325.0, 3.0], [17005.0, 2.0], [16621.0, 1.0], [16749.0, 1.0], [17837.0, 2.0], [17581.0, 2.0], [17453.0, 2.0], [17709.0, 2.0], [18349.0, 2.0], [17901.0, 2.0], [18477.0, 2.0], [18797.0, 2.0], [18541.0, 2.0], [19309.0, 2.0], [19245.0, 2.0], [18989.0, 2.0], [19053.0, 2.0], [20077.0, 2.0], [19757.0, 2.0], [19565.0, 2.0], [19501.0, 2.0], [20141.0, 2.0], [19949.0, 1.0], [20461.0, 2.0], [19693.0, 2.0], [20525.0, 2.0], [21037.0, 2.0], [20845.0, 2.0], [22893.0, 2.0], [23021.0, 2.0], [13302.0, 2.0], [13782.0, 3.0], [14358.0, 3.0], [14518.0, 3.0], [15318.0, 2.0], [15094.0, 2.0], [15382.0, 1.0], [15478.0, 2.0], [15702.0, 2.0], [15734.0, 2.0], [15574.0, 2.0], [15606.0, 2.0], [15766.0, 2.0], [16054.0, 2.0], [16876.0, 2.0], [16556.0, 2.0], [16684.0, 2.0], [17004.0, 2.0], [16492.0, 2.0], [19244.0, 2.0], [19308.0, 2.0], [19116.0, 2.0], [18732.0, 3.0], [19820.0, 2.0], [19628.0, 2.0], [19948.0, 2.0], [20268.0, 2.0], [19500.0, 2.0], [20076.0, 2.0], [19692.0, 2.0], [19756.0, 2.0], [20716.0, 2.0], [22380.0, 2.0], [22444.0, 2.0], [22956.0, 2.0], [22572.0, 2.0], [24044.0, 2.0], [26092.0, 2.0], [26860.0, 2.0], [17075.0, 2.0], [17011.0, 2.0], [18227.0, 2.0], [18035.0, 3.0], [17779.0, 2.0], [17971.0, 2.0], [18291.0, 2.0], [18099.0, 2.0], [18419.0, 3.0], [17843.0, 1.0], [18611.0, 3.0], [18995.0, 2.0], [18867.0, 2.0], [19123.0, 2.0], [18547.0, 2.0], [19571.0, 2.0], [19827.0, 2.0], [20211.0, 2.0], [19507.0, 2.0], [19763.0, 2.0], [20659.0, 2.0], [20787.0, 2.0], [21427.0, 2.0], [20723.0, 2.0], [21171.0, 2.0], [22003.0, 2.0], [23155.0, 2.0], [25139.0, 2.0], [25331.0, 2.0], [27827.0, 2.0], [13017.0, 2.0], [13849.0, 2.0], [14841.0, 2.0], [14713.0, 1.0], [15385.0, 2.0], [15897.0, 3.0], [16377.0, 2.0], [17202.0, 2.0], [16882.0, 1.0], [18418.0, 2.0], [17842.0, 2.0], [18098.0, 2.0], [17458.0, 2.0], [18162.0, 3.0], [19058.0, 2.0], [19442.0, 2.0], [18674.0, 2.0], [19570.0, 2.0], [19826.0, 2.0], [19506.0, 2.0], [19954.0, 2.0], [20466.0, 2.0], [20402.0, 2.0], [20658.0, 2.0], [21298.0, 2.0], [21170.0, 2.0], [20722.0, 2.0], [22386.0, 2.0], [22450.0, 2.0], [23154.0, 2.0], [17393.0, 2.0], [16497.0, 1.0], [16817.0, 3.0], [17585.0, 1.0], [18097.0, 2.0], [18417.0, 2.0], [18289.0, 2.0], [18545.0, 2.0], [19121.0, 2.0], [18993.0, 2.0], [19057.0, 2.0], [18609.0, 2.0], [18737.0, 2.0], [19185.0, 2.0], [19569.0, 2.0], [19697.0, 2.0], [19761.0, 2.0], [19953.0, 2.0], [20401.0, 2.0], [20337.0, 2.0], [20145.0, 2.0], [20273.0, 2.0], [21169.0, 2.0], [20657.0, 2.0], [21105.0, 2.0], [22065.0, 2.0], [23217.0, 2.0], [22769.0, 2.0], [23729.0, 2.0], [24177.0, 2.0], [25137.0, 2.0], [29681.0, 1.0], [4860.0, 1.0], [13304.0, 1.0], [14168.0, 2.0], [13976.0, 1.0], [13848.0, 1.0], [15320.0, 3.0], [15192.0, 2.0], [15256.0, 3.0], [15384.0, 2.0], [15544.0, 3.0], [15864.0, 3.0], [16056.0, 3.0], [16752.0, 2.0], [16880.0, 2.0], [16432.0, 2.0], [17392.0, 2.0], [16816.0, 2.0], [17328.0, 3.0], [17264.0, 1.0], [16688.0, 1.0], [17520.0, 2.0], [18224.0, 2.0], [18096.0, 2.0], [17456.0, 2.0], [17840.0, 2.0], [18608.0, 2.0], [18736.0, 2.0], [19120.0, 2.0], [18928.0, 2.0], [18672.0, 2.0], [19184.0, 1.0], [20272.0, 2.0], [19888.0, 2.0], [20336.0, 2.0], [19696.0, 2.0], [19632.0, 2.0], [19952.0, 2.0], [19568.0, 2.0], [19824.0, 2.0], [19760.0, 2.0], [20080.0, 2.0], [20016.0, 2.0], [20400.0, 2.0], [23472.0, 2.0], [24560.0, 2.0], [17271.0, 2.0], [16567.0, 2.0], [16823.0, 2.0], [16887.0, 2.0], [17399.0, 2.0], [16439.0, 3.0], [17015.0, 1.0], [17207.0, 2.0], [17463.0, 2.0], [18103.0, 2.0], [17719.0, 2.0], [19447.0, 2.0], [18551.0, 2.0], [19191.0, 2.0], [19319.0, 3.0], [18615.0, 2.0], [18935.0, 2.0], [19511.0, 2.0], [19831.0, 2.0], [19895.0, 2.0], [19767.0, 1.0], [20343.0, 2.0], [19703.0, 2.0], [20087.0, 2.0], [20663.0, 2.0], [21111.0, 2.0], [20599.0, 2.0], [22263.0, 2.0], [22839.0, 2.0], [23223.0, 2.0], [25335.0, 2.0], [28727.0, 2.0], [9819.0, 1.0], [14811.0, 2.0], [15835.0, 2.0], [16347.0, 2.0], [16187.0, 3.0], [17398.0, 2.0], [17206.0, 2.0], [17142.0, 2.0], [16758.0, 2.0], [18230.0, 2.0], [19318.0, 2.0], [18486.0, 2.0], [18870.0, 2.0], [18934.0, 3.0], [19190.0, 2.0], [18998.0, 2.0], [18806.0, 2.0], [18550.0, 2.0], [19254.0, 2.0], [19382.0, 2.0], [19446.0, 2.0], [20022.0, 2.0], [19766.0, 2.0], [20214.0, 1.0], [20598.0, 2.0], [21046.0, 2.0], [20534.0, 2.0], [22646.0, 2.0], [25270.0, 2.0], [26102.0, 2.0], [25846.0, 2.0], [26678.0, 2.0], [16629.0, 2.0], [17141.0, 2.0], [17653.0, 2.0], [17845.0, 2.0], [17973.0, 2.0], [18037.0, 2.0], [18165.0, 2.0], [19445.0, 2.0], [18805.0, 2.0], [18485.0, 2.0], [18741.0, 2.0], [19189.0, 2.0], [19317.0, 2.0], [18869.0, 1.0], [19701.0, 2.0], [20405.0, 2.0], [19637.0, 2.0], [20149.0, 2.0], [19957.0, 2.0], [20277.0, 2.0], [20021.0, 2.0], [19573.0, 2.0], [20085.0, 2.0], [20789.0, 2.0], [20725.0, 2.0], [21301.0, 2.0], [21045.0, 2.0], [21749.0, 2.0], [22517.0, 2.0], [22325.0, 2.0], [21877.0, 2.0], [23989.0, 2.0], [25141.0, 2.0], [28405.0, 2.0], [28213.0, 2.0], [12666.0, 2.0], [14426.0, 1.0], [14362.0, 1.0], [15162.0, 3.0], [14938.0, 2.0], [15290.0, 2.0], [15674.0, 2.0], [15418.0, 3.0], [15386.0, 2.0], [15962.0, 1.0], [17140.0, 2.0], [16820.0, 2.0], [16500.0, 2.0], [17908.0, 3.0], [17780.0, 2.0], [17716.0, 2.0], [17844.0, 3.0], [18036.0, 2.0], [18420.0, 2.0], [17524.0, 2.0], [17652.0, 2.0], [19316.0, 2.0], [18676.0, 2.0], [18548.0, 2.0], [19380.0, 2.0], [18484.0, 2.0], [18740.0, 2.0], [19124.0, 2.0], [19188.0, 3.0], [19060.0, 2.0], [18996.0, 2.0], [18868.0, 2.0], [20404.0, 2.0], [20020.0, 2.0], [20468.0, 2.0], [20212.0, 2.0], [20276.0, 2.0], [19828.0, 2.0], [20084.0, 2.0], [20148.0, 2.0], [20660.0, 2.0], [21428.0, 2.0], [21748.0, 2.0], [23284.0, 2.0], [22708.0, 2.0], [24500.0, 2.0], [16891.0, 2.0], [16699.0, 3.0], [16635.0, 3.0], [17723.0, 2.0], [19387.0, 2.0], [18747.0, 2.0], [19323.0, 2.0], [18619.0, 3.0], [19451.0, 2.0], [19515.0, 2.0], [19771.0, 2.0], [20347.0, 2.0], [19707.0, 2.0], [20027.0, 2.0], [19643.0, 2.0], [20219.0, 2.0], [21179.0, 2.0], [20923.0, 2.0], [21499.0, 2.0], [20539.0, 2.0], [24891.0, 2.0], [26107.0, 2.0], [27515.0, 2.0], [13725.0, 2.0], [15101.0, 2.0], [15293.0, 1.0], [15389.0, 3.0], [16029.0, 2.0], [16381.0, 2.0], [16061.0, 3.0], [16349.0, 1.0], [16890.0, 2.0], [17018.0, 2.0], [17082.0, 2.0], [17402.0, 2.0], [18170.0, 2.0], [17786.0, 2.0], [17978.0, 2.0], [18298.0, 1.0], [18938.0, 3.0], [18490.0, 2.0], [19002.0, 2.0], [19066.0, 2.0], [19514.0, 2.0], [19706.0, 2.0], [19770.0, 2.0], [19898.0, 2.0], [19578.0, 2.0], [20410.0, 2.0], [19642.0, 2.0], [21114.0, 2.0], [21434.0, 2.0], [21370.0, 2.0], [21818.0, 2.0], [22650.0, 2.0], [25594.0, 2.0], [16505.0, 2.0], [16569.0, 3.0], [16953.0, 2.0], [17337.0, 3.0], [16889.0, 1.0], [16825.0, 3.0], [17145.0, 1.0], [18361.0, 2.0], [18297.0, 2.0], [17977.0, 2.0], [18233.0, 2.0], [18681.0, 2.0], [19449.0, 2.0], [19129.0, 2.0], [18809.0, 2.0], [19321.0, 2.0], [19193.0, 2.0], [20025.0, 2.0], [19833.0, 2.0], [19577.0, 2.0], [19897.0, 2.0], [19705.0, 2.0], [20665.0, 2.0], [20921.0, 2.0], [21817.0, 2.0], [22585.0, 2.0], [23545.0, 2.0], [24313.0, 2.0], [25337.0, 2.0], [27513.0, 2.0], [7054.0, 1.0], [7614.0, 1.0], [13756.0, 2.0], [15388.0, 2.0], [15772.0, 3.0], [15420.0, 1.0], [15804.0, 2.0], [15548.0, 1.0], [16252.0, 2.0], [16316.0, 2.0], [15900.0, 3.0], [16124.0, 3.0], [16060.0, 3.0], [17016.0, 2.0], [18040.0, 2.0], [17592.0, 2.0], [17848.0, 2.0], [17912.0, 2.0], [17784.0, 2.0], [18296.0, 2.0], [18616.0, 2.0], [18936.0, 2.0], [19448.0, 2.0], [18680.0, 2.0], [19384.0, 1.0], [19960.0, 2.0], [19896.0, 2.0], [20408.0, 2.0], [20152.0, 2.0], [19768.0, 2.0], [20024.0, 2.0], [19512.0, 2.0], [19832.0, 2.0], [19576.0, 2.0], [20536.0, 2.0], [21112.0, 2.0], [20856.0, 2.0], [21048.0, 2.0], [20792.0, 2.0], [22456.0, 1.0], [23352.0, 2.0], [25528.0, 2.0], [26552.0, 1.0], [31160.0, 1.0], [17151.0, 2.0], [17023.0, 2.0], [17215.0, 2.0], [16959.0, 1.0], [17599.0, 2.0], [18239.0, 2.0], [18431.0, 2.0], [17855.0, 2.0], [18175.0, 2.0], [17983.0, 2.0], [17919.0, 2.0], [18815.0, 2.0], [18623.0, 2.0], [18943.0, 2.0], [18559.0, 2.0], [19135.0, 1.0], [20351.0, 2.0], [19903.0, 2.0], [19583.0, 2.0], [20095.0, 2.0], [20031.0, 2.0], [20223.0, 2.0], [19519.0, 2.0], [22335.0, 2.0], [23871.0, 2.0], [15871.0, 2.0], [15679.0, 2.0], [17342.0, 2.0], [16638.0, 3.0], [17214.0, 3.0], [17278.0, 3.0], [17726.0, 2.0], [17662.0, 2.0], [18110.0, 2.0], [18430.0, 1.0], [18750.0, 2.0], [18878.0, 2.0], [19006.0, 2.0], [18494.0, 3.0], [18814.0, 2.0], [18686.0, 3.0], [18942.0, 1.0], [20350.0, 2.0], [19646.0, 2.0], [19582.0, 2.0], [19774.0, 2.0], [19966.0, 2.0], [20222.0, 2.0], [20158.0, 2.0], [20030.0, 2.0], [20286.0, 2.0], [20670.0, 2.0], [21118.0, 2.0], [21310.0, 2.0], [20798.0, 2.0], [21886.0, 2.0], [21758.0, 2.0], [23166.0, 2.0], [25726.0, 2.0], [30846.0, 1.0], [17149.0, 3.0], [17085.0, 1.0], [16445.0, 1.0], [16637.0, 1.0], [18237.0, 2.0], [18301.0, 2.0], [17661.0, 2.0], [19005.0, 2.0], [18621.0, 2.0], [19325.0, 2.0], [19069.0, 2.0], [18941.0, 2.0], [19389.0, 2.0], [19133.0, 2.0], [19453.0, 2.0], [19965.0, 2.0], [19837.0, 2.0], [20413.0, 2.0], [19581.0, 2.0], [20221.0, 2.0], [20349.0, 2.0], [21309.0, 2.0], [21181.0, 2.0], [21949.0, 2.0], [22077.0, 2.0], [23613.0, 2.0], [25213.0, 2.0], [26429.0, 2.0], [6191.0, 0.0], [11390.0, 2.0], [12062.0, 1.0], [14654.0, 2.0], [15230.0, 1.0], [15614.0, 2.0], [15582.0, 2.0], [15710.0, 2.0], [15806.0, 2.0], [16126.0, 2.0], [16382.0, 2.0], [16158.0, 1.0], [16956.0, 2.0], [16828.0, 2.0], [17148.0, 2.0], [17212.0, 1.0], [18108.0, 2.0], [17532.0, 2.0], [18300.0, 3.0], [17852.0, 1.0], [19324.0, 2.0], [19004.0, 2.0], [19196.0, 2.0], [19260.0, 2.0], [19132.0, 2.0], [18940.0, 2.0], [18748.0, 2.0], [18812.0, 3.0], [19708.0, 2.0], [20220.0, 2.0], [19580.0, 2.0], [21116.0, 2.0], [20604.0, 2.0], [20668.0, 2.0], [20540.0, 2.0], [21180.0, 2.0], [21564.0, 2.0], [23868.0, 2.0], [24060.0, 2.0], [26044.0, 2.0], [27196.0, 1.0], [27836.0, 2.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 32489.0, "title": "Response Time Vs Request"}},
    getOptions: function() {
        return {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Response Time in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: {
                noColumns: 2,
                show: true,
                container: '#legendResponseTimeVsRequest'
            },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median response time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesResponseTimeVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotResponseTimeVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewResponseTimeVsRequest"), dataset, prepareOverviewOptions(options));

    }
};

// Response Time vs Request
function refreshResponseTimeVsRequest() {
    var infos = responseTimeVsRequestInfos;
    prepareSeries(infos.data);
    if (isGraph($("#flotResponseTimeVsRequest"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeVsRequest");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimeVsRequest", "#overviewResponseTimeVsRequest");
        $('#footerResponseRimeVsRequest .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var latenciesVsRequestInfos = {
    data: {"result": {"minY": 0.0, "minX": 2100.0, "maxY": 4.0, "series": [{"data": [[16707.0, 1.0], [17091.0, 2.0], [16387.0, 2.0], [17987.0, 2.0], [17731.0, 2.0], [17923.0, 2.0], [18691.0, 2.0], [18499.0, 2.0], [19075.0, 2.0], [20227.0, 2.0], [19587.0, 2.0], [19779.0, 2.0], [20291.0, 2.0], [19907.0, 2.0], [21251.0, 2.0], [20995.0, 2.0], [21187.0, 2.0], [22467.0, 2.0], [23043.0, 2.0], [24963.0, 2.0], [26435.0, 1.0], [27267.0, 2.0], [27715.0, 1.0], [12449.0, 1.0], [14049.0, 3.0], [14433.0, 3.0], [14497.0, 2.0], [15137.0, 2.0], [14945.0, 2.0], [15617.0, 2.0], [15521.0, 3.0], [15361.0, 2.0], [15937.0, 2.0], [16225.0, 3.0], [16962.0, 2.0], [16770.0, 2.0], [16450.0, 2.0], [17154.0, 3.0], [16514.0, 2.0], [16706.0, 3.0], [17346.0, 1.0], [17666.0, 2.0], [17474.0, 2.0], [18370.0, 2.0], [18114.0, 1.0], [19074.0, 2.0], [18498.0, 2.0], [19202.0, 2.0], [18754.0, 2.0], [18562.0, 2.0], [19394.0, 2.0], [19650.0, 2.0], [20098.0, 2.0], [19714.0, 2.0], [19906.0, 2.0], [19778.0, 2.0], [20034.0, 2.0], [21250.0, 2.0], [21378.0, 2.0], [21634.0, 2.0], [24130.0, 2.0], [16961.0, 2.0], [16769.0, 1.0], [18177.0, 2.0], [18113.0, 2.0], [17537.0, 2.0], [19201.0, 2.0], [19265.0, 2.0], [18753.0, 2.0], [18817.0, 1.0], [18689.0, 2.0], [18433.0, 2.0], [18625.0, 1.0], [19009.0, 2.0], [19137.0, 2.0], [19905.0, 2.0], [19841.0, 2.0], [20289.0, 2.0], [19585.0, 2.0], [20225.0, 2.0], [19777.0, 2.0], [19649.0, 2.0], [19521.0, 2.0], [20865.0, 2.0], [20545.0, 2.0], [20481.0, 2.0], [20737.0, 2.0], [22081.0, 2.0], [22465.0, 2.0], [24385.0, 2.0], [25665.0, 2.0], [13408.0, 1.0], [14560.0, 2.0], [14784.0, 1.0], [15168.0, 2.0], [15552.0, 3.0], [15968.0, 2.0], [16352.0, 2.0], [16576.0, 3.0], [16768.0, 3.0], [16448.0, 2.0], [18368.0, 2.0], [18304.0, 2.0], [19392.0, 2.0], [19072.0, 2.0], [19008.0, 2.0], [18816.0, 2.0], [18688.0, 1.0], [18624.0, 1.0], [18560.0, 1.0], [19584.0, 2.0], [19776.0, 2.0], [20032.0, 2.0], [20096.0, 2.0], [19968.0, 2.0], [20416.0, 2.0], [19648.0, 2.0], [19840.0, 2.0], [19520.0, 2.0], [19456.0, 2.0], [19712.0, 2.0], [21312.0, 2.0], [20544.0, 2.0], [20608.0, 2.0], [20672.0, 2.0], [20480.0, 1.0], [21632.0, 2.0], [23168.0, 2.0], [23424.0, 2.0], [24768.0, 2.0], [25728.0, 2.0], [25600.0, 2.0], [16519.0, 1.0], [17159.0, 2.0], [17287.0, 2.0], [16583.0, 3.0], [17799.0, 2.0], [18183.0, 2.0], [19335.0, 2.0], [19271.0, 2.0], [19079.0, 2.0], [19143.0, 2.0], [18887.0, 2.0], [19911.0, 2.0], [19591.0, 2.0], [19847.0, 2.0], [19463.0, 2.0], [19975.0, 2.0], [19527.0, 2.0], [20231.0, 2.0], [19655.0, 2.0], [21127.0, 2.0], [20935.0, 2.0], [20807.0, 2.0], [20743.0, 2.0], [21255.0, 2.0], [23495.0, 2.0], [24199.0, 2.0], [23815.0, 2.0], [23623.0, 2.0], [14051.0, 2.0], [14243.0, 2.0], [14275.0, 2.0], [14307.0, 2.0], [14179.0, 1.0], [14467.0, 2.0], [14435.0, 2.0], [15107.0, 1.0], [15267.0, 1.0], [15683.0, 2.0], [16355.0, 2.0], [17222.0, 2.0], [17158.0, 2.0], [16838.0, 3.0], [16646.0, 3.0], [16774.0, 3.0], [16710.0, 2.0], [17542.0, 2.0], [18374.0, 2.0], [18118.0, 2.0], [17926.0, 2.0], [18054.0, 2.0], [19206.0, 2.0], [19398.0, 2.0], [19334.0, 2.0], [19014.0, 2.0], [18758.0, 2.0], [19142.0, 2.0], [18630.0, 2.0], [19526.0, 2.0], [19910.0, 2.0], [19718.0, 2.0], [19846.0, 2.0], [20038.0, 2.0], [19782.0, 2.0], [20998.0, 1.0], [20742.0, 2.0], [20486.0, 2.0], [22278.0, 2.0], [21510.0, 2.0], [27334.0, 2.0], [17285.0, 2.0], [16645.0, 1.0], [16517.0, 1.0], [17733.0, 2.0], [18373.0, 2.0], [17797.0, 2.0], [18181.0, 2.0], [17989.0, 2.0], [18117.0, 2.0], [19205.0, 2.0], [18757.0, 3.0], [19077.0, 2.0], [18629.0, 2.0], [18693.0, 2.0], [18437.0, 2.0], [18821.0, 2.0], [19909.0, 2.0], [19717.0, 2.0], [20293.0, 2.0], [20165.0, 2.0], [19973.0, 2.0], [20229.0, 2.0], [19781.0, 2.0], [20037.0, 2.0], [20485.0, 2.0], [20613.0, 2.0], [20805.0, 2.0], [21893.0, 2.0], [22277.0, 2.0], [21701.0, 2.0], [23877.0, 2.0], [24645.0, 2.0], [26245.0, 2.0], [25605.0, 2.0], [11522.0, 1.0], [12866.0, 1.0], [13954.0, 2.0], [14786.0, 3.0], [14818.0, 2.0], [14466.0, 2.0], [14530.0, 2.0], [15170.0, 2.0], [15554.0, 2.0], [16322.0, 3.0], [16388.0, 2.0], [16772.0, 3.0], [17156.0, 2.0], [17220.0, 2.0], [17284.0, 1.0], [18116.0, 1.0], [18692.0, 2.0], [19268.0, 2.0], [18436.0, 2.0], [19844.0, 2.0], [20292.0, 2.0], [19460.0, 2.0], [20420.0, 2.0], [20100.0, 2.0], [19716.0, 2.0], [20484.0, 2.0], [20804.0, 2.0], [21380.0, 2.0], [22404.0, 2.0], [24068.0, 2.0], [23556.0, 2.0], [24772.0, 2.0], [24580.0, 2.0], [30980.0, 1.0], [16523.0, 3.0], [17163.0, 3.0], [17035.0, 2.0], [16651.0, 3.0], [16907.0, 1.0], [16459.0, 1.0], [16843.0, 1.0], [18251.0, 2.0], [18123.0, 2.0], [17611.0, 2.0], [17995.0, 2.0], [18187.0, 2.0], [19275.0, 2.0], [19339.0, 2.0], [18443.0, 2.0], [18507.0, 2.0], [18571.0, 2.0], [18955.0, 2.0], [19147.0, 2.0], [18763.0, 2.0], [19467.0, 2.0], [19531.0, 2.0], [19979.0, 2.0], [21003.0, 2.0], [20683.0, 2.0], [20747.0, 2.0], [21131.0, 2.0], [22155.0, 2.0], [21579.0, 2.0], [23307.0, 2.0], [22603.0, 2.0], [22859.0, 2.0], [24011.0, 2.0], [28235.0, 1.0], [14213.0, 2.0], [13893.0, 2.0], [14661.0, 3.0], [15237.0, 3.0], [15781.0, 2.0], [15589.0, 2.0], [15621.0, 1.0], [16293.0, 2.0], [16197.0, 2.0], [15909.0, 1.0], [16650.0, 3.0], [17034.0, 3.0], [16842.0, 3.0], [17290.0, 2.0], [18378.0, 3.0], [18122.0, 2.0], [17610.0, 3.0], [17930.0, 3.0], [19338.0, 2.0], [19274.0, 2.0], [19146.0, 2.0], [18826.0, 2.0], [20234.0, 2.0], [19466.0, 2.0], [19978.0, 2.0], [19658.0, 2.0], [20426.0, 2.0], [19914.0, 2.0], [20106.0, 2.0], [19786.0, 2.0], [19530.0, 2.0], [20682.0, 2.0], [21514.0, 2.0], [22666.0, 2.0], [23434.0, 2.0], [24586.0, 2.0], [24714.0, 2.0], [26634.0, 2.0], [17289.0, 2.0], [16585.0, 2.0], [17097.0, 2.0], [17353.0, 2.0], [16713.0, 2.0], [16457.0, 3.0], [17737.0, 3.0], [18185.0, 3.0], [17609.0, 3.0], [18313.0, 2.0], [17673.0, 2.0], [17481.0, 2.0], [19337.0, 2.0], [19081.0, 2.0], [18441.0, 2.0], [18761.0, 2.0], [19401.0, 2.0], [19209.0, 2.0], [18505.0, 1.0], [20233.0, 2.0], [20425.0, 2.0], [19849.0, 2.0], [19465.0, 2.0], [20361.0, 2.0], [19977.0, 2.0], [20041.0, 2.0], [21065.0, 2.0], [21897.0, 2.0], [21833.0, 2.0], [24777.0, 2.0], [14884.0, 2.0], [15844.0, 3.0], [15396.0, 2.0], [15620.0, 3.0], [16132.0, 2.0], [16164.0, 2.0], [16068.0, 3.0], [16292.0, 3.0], [17352.0, 2.0], [16456.0, 2.0], [16904.0, 2.0], [16584.0, 3.0], [17032.0, 2.0], [16968.0, 1.0], [16392.0, 1.0], [17160.0, 1.0], [17736.0, 2.0], [17864.0, 2.0], [18376.0, 2.0], [17672.0, 2.0], [17800.0, 1.0], [18184.0, 1.0], [19080.0, 2.0], [18440.0, 2.0], [18696.0, 3.0], [19336.0, 2.0], [19144.0, 2.0], [19272.0, 2.0], [18568.0, 2.0], [18952.0, 2.0], [19656.0, 2.0], [19848.0, 2.0], [20424.0, 2.0], [20296.0, 2.0], [20040.0, 2.0], [20104.0, 2.0], [19592.0, 2.0], [20616.0, 2.0], [20744.0, 2.0], [21320.0, 2.0], [21256.0, 2.0], [20872.0, 2.0], [21576.0, 2.0], [21640.0, 2.0], [23240.0, 2.0], [27720.0, 1.0], [30920.0, 1.0], [16783.0, 2.0], [16719.0, 3.0], [16975.0, 2.0], [16911.0, 2.0], [17743.0, 2.0], [17935.0, 2.0], [18127.0, 2.0], [19215.0, 2.0], [19407.0, 2.0], [18575.0, 2.0], [19023.0, 2.0], [19343.0, 2.0], [19087.0, 2.0], [19663.0, 2.0], [19599.0, 2.0], [20111.0, 2.0], [20175.0, 2.0], [19535.0, 2.0], [19855.0, 2.0], [20303.0, 2.0], [20367.0, 2.0], [19471.0, 2.0], [20047.0, 2.0], [21135.0, 2.0], [21007.0, 2.0], [21583.0, 2.0], [22031.0, 2.0], [21903.0, 2.0], [22863.0, 2.0], [23951.0, 2.0], [24783.0, 2.0], [25167.0, 2.0], [8551.0, 1.0], [14631.0, 2.0], [14567.0, 2.0], [15847.0, 2.0], [15687.0, 2.0], [15559.0, 3.0], [16135.0, 3.0], [16231.0, 3.0], [16007.0, 2.0], [16398.0, 2.0], [17038.0, 2.0], [16846.0, 1.0], [17550.0, 2.0], [18062.0, 2.0], [17806.0, 2.0], [17614.0, 2.0], [17870.0, 2.0], [18958.0, 2.0], [18638.0, 2.0], [19086.0, 2.0], [19022.0, 2.0], [18446.0, 2.0], [18766.0, 2.0], [18510.0, 2.0], [19342.0, 2.0], [19278.0, 2.0], [20302.0, 2.0], [19726.0, 2.0], [20174.0, 2.0], [19982.0, 2.0], [19534.0, 2.0], [20878.0, 2.0], [21134.0, 2.0], [20558.0, 2.0], [24526.0, 2.0], [24014.0, 2.0], [25422.0, 2.0], [26382.0, 2.0], [25678.0, 2.0], [26766.0, 2.0], [26702.0, 2.0], [28046.0, 2.0], [17037.0, 2.0], [16589.0, 2.0], [16781.0, 2.0], [16717.0, 3.0], [17165.0, 2.0], [16909.0, 2.0], [17485.0, 3.0], [18253.0, 2.0], [17677.0, 3.0], [18061.0, 3.0], [18445.0, 2.0], [19405.0, 2.0], [19341.0, 2.0], [18765.0, 2.0], [19021.0, 2.0], [19661.0, 2.0], [19469.0, 2.0], [19981.0, 2.0], [20429.0, 2.0], [20237.0, 2.0], [19917.0, 2.0], [20109.0, 2.0], [19725.0, 2.0], [20749.0, 2.0], [21197.0, 2.0], [21517.0, 2.0], [22477.0, 2.0], [23309.0, 2.0], [24333.0, 2.0], [25165.0, 2.0], [13254.0, 2.0], [13446.0, 3.0], [14182.0, 1.0], [15334.0, 2.0], [14982.0, 2.0], [15558.0, 2.0], [15718.0, 3.0], [16166.0, 2.0], [16326.0, 2.0], [15878.0, 2.0], [16262.0, 3.0], [16396.0, 2.0], [16652.0, 3.0], [17100.0, 1.0], [16908.0, 2.0], [16460.0, 1.0], [16844.0, 1.0], [17804.0, 2.0], [17932.0, 2.0], [17484.0, 2.0], [17548.0, 1.0], [17676.0, 1.0], [18572.0, 2.0], [19020.0, 2.0], [19276.0, 2.0], [18956.0, 3.0], [19468.0, 2.0], [19788.0, 2.0], [19724.0, 2.0], [20044.0, 2.0], [19660.0, 2.0], [19532.0, 2.0], [19852.0, 2.0], [19596.0, 2.0], [20684.0, 2.0], [21004.0, 2.0], [22412.0, 2.0], [23436.0, 2.0], [23372.0, 2.0], [31372.0, 1.0], [17363.0, 1.0], [19411.0, 2.0], [19027.0, 2.0], [19091.0, 2.0], [19155.0, 2.0], [18451.0, 2.0], [18515.0, 2.0], [18963.0, 2.0], [19283.0, 2.0], [19219.0, 2.0], [18643.0, 2.0], [18771.0, 2.0], [18899.0, 2.0], [20243.0, 2.0], [19539.0, 2.0], [20179.0, 2.0], [20435.0, 2.0], [20051.0, 2.0], [20947.0, 2.0], [21331.0, 2.0], [21523.0, 2.0], [23571.0, 2.0], [26643.0, 2.0], [29715.0, 1.0], [13097.0, 2.0], [13929.0, 2.0], [14825.0, 2.0], [15273.0, 1.0], [15561.0, 2.0], [15817.0, 2.0], [16073.0, 2.0], [16169.0, 2.0], [15977.0, 2.0], [17106.0, 2.0], [16850.0, 2.0], [16594.0, 3.0], [16402.0, 3.0], [16978.0, 2.0], [18066.0, 2.0], [17554.0, 2.0], [18450.0, 2.0], [19090.0, 2.0], [19154.0, 2.0], [19282.0, 2.0], [19346.0, 2.0], [19922.0, 2.0], [19538.0, 2.0], [20114.0, 2.0], [20306.0, 2.0], [20370.0, 2.0], [19858.0, 2.0], [19474.0, 2.0], [20882.0, 2.0], [21138.0, 2.0], [21394.0, 2.0], [21074.0, 2.0], [20626.0, 2.0], [20498.0, 2.0], [21202.0, 2.0], [23506.0, 2.0], [23762.0, 2.0], [26514.0, 2.0], [26706.0, 1.0], [31186.0, 1.0], [17233.0, 2.0], [16529.0, 2.0], [16401.0, 2.0], [16721.0, 3.0], [17361.0, 2.0], [16913.0, 3.0], [17297.0, 1.0], [17425.0, 2.0], [17617.0, 3.0], [17681.0, 3.0], [17553.0, 2.0], [18769.0, 2.0], [19409.0, 2.0], [19217.0, 2.0], [18897.0, 2.0], [18833.0, 2.0], [19281.0, 2.0], [18641.0, 2.0], [19601.0, 2.0], [19857.0, 2.0], [19729.0, 2.0], [19473.0, 2.0], [19985.0, 2.0], [20433.0, 2.0], [19921.0, 2.0], [20369.0, 2.0], [20305.0, 2.0], [20881.0, 2.0], [20625.0, 2.0], [21009.0, 2.0], [21521.0, 2.0], [22929.0, 2.0], [24337.0, 2.0], [27665.0, 2.0], [14536.0, 2.0], [15144.0, 1.0], [15560.0, 3.0], [15400.0, 2.0], [15496.0, 3.0], [15592.0, 1.0], [16264.0, 2.0], [15912.0, 1.0], [17168.0, 2.0], [16976.0, 1.0], [18000.0, 2.0], [19280.0, 2.0], [18960.0, 2.0], [18640.0, 2.0], [19216.0, 2.0], [19024.0, 2.0], [18704.0, 2.0], [18896.0, 2.0], [18832.0, 2.0], [19920.0, 2.0], [19664.0, 2.0], [19984.0, 2.0], [19856.0, 2.0], [20112.0, 2.0], [20176.0, 2.0], [21072.0, 2.0], [21328.0, 2.0], [21904.0, 2.0], [23056.0, 2.0], [24272.0, 2.0], [26384.0, 2.0], [26896.0, 2.0], [17111.0, 3.0], [16983.0, 3.0], [16663.0, 2.0], [16919.0, 2.0], [17751.0, 2.0], [17815.0, 2.0], [17431.0, 2.0], [17495.0, 3.0], [17943.0, 3.0], [18071.0, 3.0], [19223.0, 2.0], [18519.0, 2.0], [18967.0, 2.0], [18839.0, 2.0], [18711.0, 2.0], [19287.0, 2.0], [19735.0, 2.0], [20439.0, 2.0], [19543.0, 2.0], [20375.0, 2.0], [19607.0, 2.0], [20247.0, 2.0], [19799.0, 2.0], [19671.0, 2.0], [20823.0, 2.0], [20503.0, 2.0], [20695.0, 2.0], [21207.0, 2.0], [21975.0, 2.0], [22935.0, 2.0], [24279.0, 2.0], [24407.0, 2.0], [24727.0, 2.0], [24983.0, 2.0], [25431.0, 2.0], [24663.0, 2.0], [26519.0, 2.0], [26583.0, 2.0], [26199.0, 2.0], [27735.0, 2.0], [13259.0, 2.0], [15659.0, 2.0], [15819.0, 3.0], [15755.0, 3.0], [16043.0, 3.0], [16171.0, 3.0], [16235.0, 3.0], [17046.0, 2.0], [17174.0, 2.0], [16470.0, 2.0], [17494.0, 2.0], [17686.0, 3.0], [17942.0, 2.0], [18966.0, 2.0], [19094.0, 2.0], [19158.0, 2.0], [19030.0, 2.0], [19350.0, 2.0], [19286.0, 2.0], [18902.0, 2.0], [19990.0, 2.0], [20310.0, 2.0], [20758.0, 2.0], [22486.0, 2.0], [22614.0, 2.0], [23446.0, 2.0], [27862.0, 2.0], [16917.0, 2.0], [16725.0, 2.0], [16469.0, 2.0], [16533.0, 3.0], [17109.0, 2.0], [17045.0, 2.0], [16661.0, 1.0], [17493.0, 2.0], [17941.0, 2.0], [17877.0, 2.0], [17685.0, 2.0], [19157.0, 2.0], [19413.0, 2.0], [18901.0, 2.0], [18645.0, 2.0], [18453.0, 3.0], [18837.0, 2.0], [19861.0, 2.0], [19989.0, 2.0], [19541.0, 2.0], [20117.0, 2.0], [20821.0, 2.0], [20885.0, 2.0], [21333.0, 2.0], [21717.0, 2.0], [21589.0, 2.0], [23509.0, 2.0], [24853.0, 2.0], [26197.0, 1.0], [12202.0, 1.0], [13130.0, 2.0], [13482.0, 2.0], [13930.0, 3.0], [14250.0, 1.0], [13834.0, 1.0], [15178.0, 2.0], [15338.0, 2.0], [15786.0, 2.0], [15466.0, 2.0], [16234.0, 3.0], [16330.0, 3.0], [16266.0, 2.0], [16788.0, 2.0], [16532.0, 2.0], [17108.0, 2.0], [16404.0, 3.0], [17556.0, 3.0], [18132.0, 2.0], [19348.0, 2.0], [19220.0, 2.0], [19092.0, 2.0], [18644.0, 2.0], [18452.0, 2.0], [18708.0, 2.0], [18836.0, 2.0], [19604.0, 2.0], [19924.0, 2.0], [20180.0, 2.0], [20308.0, 2.0], [19860.0, 2.0], [20820.0, 2.0], [20500.0, 2.0], [21076.0, 2.0], [20628.0, 2.0], [20884.0, 2.0], [22228.0, 2.0], [22932.0, 2.0], [24020.0, 1.0], [24340.0, 2.0], [23828.0, 2.0], [28180.0, 2.0], [16411.0, 2.0], [16923.0, 2.0], [17563.0, 3.0], [18331.0, 2.0], [18011.0, 3.0], [18843.0, 2.0], [19419.0, 2.0], [19163.0, 2.0], [19035.0, 2.0], [20251.0, 2.0], [19675.0, 2.0], [20443.0, 2.0], [19803.0, 2.0], [19739.0, 2.0], [20315.0, 2.0], [20507.0, 2.0], [21531.0, 2.0], [21787.0, 2.0], [22491.0, 2.0], [24859.0, 2.0], [25691.0, 2.0], [26523.0, 2.0], [27035.0, 2.0], [27163.0, 2.0], [16269.0, 3.0], [15949.0, 2.0], [16205.0, 1.0], [16474.0, 2.0], [16730.0, 2.0], [16410.0, 2.0], [16922.0, 2.0], [16986.0, 3.0], [16666.0, 1.0], [17754.0, 2.0], [18010.0, 2.0], [17626.0, 2.0], [18330.0, 2.0], [18906.0, 1.0], [19098.0, 2.0], [18778.0, 2.0], [19290.0, 2.0], [19354.0, 2.0], [18458.0, 2.0], [19930.0, 2.0], [19546.0, 2.0], [19674.0, 2.0], [19802.0, 2.0], [19866.0, 2.0], [20250.0, 2.0], [20378.0, 2.0], [19994.0, 2.0], [20058.0, 2.0], [20954.0, 2.0], [20634.0, 2.0], [20506.0, 2.0], [22234.0, 2.0], [21530.0, 2.0], [22426.0, 2.0], [21978.0, 2.0], [24602.0, 2.0], [24666.0, 2.0], [16985.0, 2.0], [16601.0, 3.0], [17049.0, 1.0], [18329.0, 2.0], [18137.0, 2.0], [17945.0, 2.0], [17497.0, 2.0], [17881.0, 2.0], [18265.0, 2.0], [19353.0, 2.0], [19033.0, 2.0], [18777.0, 2.0], [18969.0, 2.0], [19225.0, 2.0], [19545.0, 2.0], [19481.0, 2.0], [20057.0, 2.0], [20185.0, 2.0], [20377.0, 2.0], [19865.0, 2.0], [20313.0, 2.0], [19993.0, 2.0], [20761.0, 2.0], [20633.0, 2.0], [21657.0, 2.0], [22617.0, 2.0], [23449.0, 2.0], [23769.0, 2.0], [24857.0, 2.0], [14060.0, 2.0], [14668.0, 2.0], [14828.0, 2.0], [14604.0, 1.0], [15084.0, 1.0], [15692.0, 2.0], [15468.0, 3.0], [16364.0, 2.0], [16268.0, 2.0], [16076.0, 3.0], [16300.0, 1.0], [17176.0, 3.0], [17112.0, 2.0], [17560.0, 2.0], [18136.0, 2.0], [17752.0, 2.0], [17688.0, 2.0], [18392.0, 2.0], [17816.0, 2.0], [18072.0, 2.0], [17496.0, 1.0], [17624.0, 1.0], [19032.0, 2.0], [18968.0, 2.0], [18904.0, 2.0], [18712.0, 3.0], [18520.0, 2.0], [19608.0, 2.0], [21272.0, 2.0], [20824.0, 2.0], [21656.0, 2.0], [22104.0, 2.0], [22040.0, 2.0], [22872.0, 2.0], [23320.0, 2.0], [24280.0, 2.0], [24408.0, 2.0], [24920.0, 2.0], [25688.0, 2.0], [26840.0, 2.0], [17375.0, 2.0], [17119.0, 2.0], [17055.0, 2.0], [16415.0, 1.0], [17247.0, 1.0], [17695.0, 2.0], [18399.0, 2.0], [17887.0, 2.0], [18079.0, 2.0], [18207.0, 2.0], [19039.0, 2.0], [18655.0, 2.0], [18847.0, 2.0], [18719.0, 2.0], [19295.0, 2.0], [19423.0, 2.0], [19551.0, 2.0], [20127.0, 2.0], [19935.0, 2.0], [19487.0, 2.0], [19743.0, 2.0], [20447.0, 2.0], [20383.0, 2.0], [19871.0, 2.0], [20063.0, 2.0], [20319.0, 2.0], [19679.0, 2.0], [21343.0, 2.0], [21727.0, 2.0], [22047.0, 2.0], [23519.0, 2.0], [23583.0, 2.0], [24351.0, 2.0], [25759.0, 2.0], [29535.0, 1.0], [9775.0, 1.0], [13967.0, 2.0], [13903.0, 2.0], [14735.0, 1.0], [15183.0, 2.0], [14927.0, 2.0], [15023.0, 2.0], [15503.0, 2.0], [16239.0, 2.0], [16175.0, 2.0], [16207.0, 2.0], [16271.0, 2.0], [15951.0, 3.0], [17182.0, 2.0], [16670.0, 2.0], [16414.0, 2.0], [16478.0, 2.0], [17822.0, 2.0], [17630.0, 3.0], [18078.0, 2.0], [17694.0, 3.0], [18334.0, 2.0], [18014.0, 3.0], [18526.0, 2.0], [19166.0, 2.0], [18654.0, 2.0], [19550.0, 2.0], [19742.0, 2.0], [19486.0, 2.0], [19614.0, 2.0], [19998.0, 2.0], [20190.0, 2.0], [20126.0, 2.0], [20638.0, 2.0], [24542.0, 2.0], [23646.0, 2.0], [26462.0, 2.0], [26910.0, 2.0], [16605.0, 3.0], [17053.0, 3.0], [17757.0, 2.0], [17693.0, 2.0], [17629.0, 2.0], [18205.0, 2.0], [17949.0, 2.0], [18269.0, 2.0], [19293.0, 2.0], [18461.0, 2.0], [18589.0, 2.0], [19037.0, 2.0], [18525.0, 2.0], [19229.0, 2.0], [20061.0, 2.0], [20317.0, 2.0], [19933.0, 2.0], [19997.0, 2.0], [20573.0, 2.0], [21597.0, 2.0], [22429.0, 2.0], [27805.0, 2.0], [13230.0, 2.0], [14350.0, 3.0], [14510.0, 2.0], [14734.0, 2.0], [14702.0, 2.0], [15182.0, 2.0], [15310.0, 3.0], [14990.0, 2.0], [15598.0, 2.0], [15438.0, 1.0], [16238.0, 2.0], [16206.0, 2.0], [16334.0, 3.0], [15950.0, 2.0], [16110.0, 2.0], [16476.0, 3.0], [16796.0, 2.0], [17308.0, 3.0], [16412.0, 3.0], [17372.0, 2.0], [16668.0, 3.0], [16988.0, 2.0], [17180.0, 1.0], [16604.0, 1.0], [18012.0, 2.0], [17628.0, 2.0], [18076.0, 2.0], [18268.0, 2.0], [18652.0, 2.0], [19228.0, 2.0], [18972.0, 2.0], [19420.0, 2.0], [18716.0, 2.0], [18844.0, 2.0], [19356.0, 2.0], [18524.0, 2.0], [18908.0, 2.0], [19676.0, 2.0], [19932.0, 2.0], [20252.0, 2.0], [19548.0, 2.0], [20124.0, 2.0], [20188.0, 2.0], [19740.0, 2.0], [20316.0, 2.0], [20380.0, 2.0], [19804.0, 2.0], [21724.0, 2.0], [22428.0, 2.0], [21980.0, 2.0], [21660.0, 2.0], [22620.0, 2.0], [22556.0, 2.0], [16547.0, 2.0], [16611.0, 1.0], [17379.0, 2.0], [16675.0, 1.0], [18083.0, 2.0], [17699.0, 2.0], [17507.0, 2.0], [17955.0, 2.0], [17827.0, 2.0], [17763.0, 2.0], [18979.0, 2.0], [19363.0, 2.0], [19235.0, 2.0], [19427.0, 2.0], [18467.0, 1.0], [19811.0, 2.0], [20387.0, 2.0], [20323.0, 2.0], [20643.0, 2.0], [22051.0, 2.0], [22179.0, 2.0], [23395.0, 2.0], [22755.0, 2.0], [24483.0, 2.0], [24099.0, 2.0], [25571.0, 2.0], [25059.0, 2.0], [13713.0, 3.0], [14737.0, 2.0], [15569.0, 2.0], [16369.0, 2.0], [16081.0, 2.0], [15953.0, 2.0], [16177.0, 3.0], [16017.0, 3.0], [16482.0, 2.0], [17122.0, 2.0], [16738.0, 2.0], [16802.0, 3.0], [16610.0, 1.0], [17186.0, 2.0], [17314.0, 1.0], [17826.0, 2.0], [17698.0, 3.0], [18082.0, 2.0], [18402.0, 2.0], [18338.0, 2.0], [17570.0, 2.0], [19042.0, 2.0], [18594.0, 2.0], [18658.0, 2.0], [18722.0, 2.0], [19810.0, 2.0], [19746.0, 1.0], [20066.0, 2.0], [20386.0, 2.0], [19618.0, 2.0], [19682.0, 2.0], [20450.0, 2.0], [21410.0, 2.0], [20770.0, 2.0], [20834.0, 2.0], [20642.0, 2.0], [22434.0, 2.0], [23266.0, 2.0], [23778.0, 2.0], [24290.0, 2.0], [24738.0, 2.0], [27362.0, 2.0], [16737.0, 2.0], [16865.0, 2.0], [17057.0, 1.0], [16929.0, 2.0], [16993.0, 2.0], [17761.0, 2.0], [17569.0, 2.0], [18849.0, 2.0], [19233.0, 2.0], [19425.0, 2.0], [18529.0, 2.0], [19489.0, 2.0], [20001.0, 2.0], [19809.0, 2.0], [19937.0, 2.0], [20833.0, 2.0], [21409.0, 2.0], [20769.0, 2.0], [22497.0, 2.0], [22177.0, 1.0], [25057.0, 2.0], [27041.0, 2.0], [26721.0, 2.0], [29089.0, 1.0], [2100.0, 0.0], [13264.0, 4.0], [14128.0, 2.0], [14384.0, 2.0], [15216.0, 3.0], [15312.0, 2.0], [15408.0, 2.0], [15440.0, 2.0], [15824.0, 3.0], [15568.0, 3.0], [15696.0, 3.0], [15888.0, 3.0], [16240.0, 3.0], [16928.0, 2.0], [17120.0, 2.0], [17056.0, 1.0], [16480.0, 1.0], [18144.0, 2.0], [18400.0, 2.0], [18592.0, 2.0], [18656.0, 2.0], [19232.0, 2.0], [18848.0, 2.0], [19040.0, 2.0], [19296.0, 2.0], [18912.0, 2.0], [20256.0, 2.0], [20128.0, 2.0], [19616.0, 2.0], [19680.0, 2.0], [19808.0, 2.0], [19552.0, 2.0], [20064.0, 2.0], [20576.0, 2.0], [20896.0, 2.0], [21728.0, 2.0], [22624.0, 2.0], [23520.0, 2.0], [23456.0, 2.0], [25120.0, 2.0], [25568.0, 2.0], [25824.0, 2.0], [16551.0, 2.0], [16807.0, 3.0], [17191.0, 1.0], [16935.0, 1.0], [18215.0, 2.0], [18151.0, 2.0], [18343.0, 2.0], [18023.0, 2.0], [19367.0, 2.0], [18791.0, 2.0], [19047.0, 2.0], [18983.0, 2.0], [19239.0, 2.0], [19495.0, 2.0], [20135.0, 2.0], [20199.0, 2.0], [19815.0, 2.0], [20839.0, 2.0], [20519.0, 2.0], [20967.0, 2.0], [21735.0, 2.0], [22311.0, 2.0], [23015.0, 2.0], [23335.0, 2.0], [24231.0, 2.0], [24295.0, 2.0], [24871.0, 2.0], [25383.0, 2.0], [27623.0, 2.0], [13651.0, 3.0], [13843.0, 2.0], [13907.0, 2.0], [15539.0, 2.0], [16147.0, 2.0], [16243.0, 2.0], [17318.0, 2.0], [16870.0, 3.0], [16806.0, 3.0], [16614.0, 2.0], [17062.0, 1.0], [17254.0, 1.0], [18214.0, 3.0], [17446.0, 2.0], [18086.0, 2.0], [18918.0, 2.0], [19238.0, 2.0], [19430.0, 2.0], [18726.0, 2.0], [18790.0, 1.0], [19110.0, 2.0], [19174.0, 2.0], [18470.0, 2.0], [20262.0, 2.0], [19622.0, 2.0], [20326.0, 2.0], [20454.0, 2.0], [19942.0, 2.0], [19494.0, 2.0], [19750.0, 2.0], [20070.0, 2.0], [20646.0, 2.0], [20966.0, 2.0], [20902.0, 2.0], [20774.0, 2.0], [21350.0, 2.0], [21990.0, 2.0], [21606.0, 2.0], [22502.0, 2.0], [23078.0, 2.0], [23014.0, 2.0], [24230.0, 2.0], [24486.0, 2.0], [24806.0, 2.0], [27110.0, 2.0], [27430.0, 2.0], [16485.0, 2.0], [16741.0, 2.0], [16421.0, 3.0], [17125.0, 1.0], [17381.0, 1.0], [17445.0, 2.0], [18277.0, 2.0], [17893.0, 2.0], [18021.0, 2.0], [18725.0, 2.0], [18853.0, 2.0], [19429.0, 2.0], [18533.0, 1.0], [19941.0, 2.0], [19813.0, 2.0], [19749.0, 2.0], [20069.0, 2.0], [19877.0, 2.0], [19621.0, 2.0], [19557.0, 2.0], [19685.0, 2.0], [20389.0, 2.0], [21093.0, 2.0], [21029.0, 2.0], [21349.0, 2.0], [20837.0, 2.0], [21797.0, 2.0], [21541.0, 2.0], [23077.0, 2.0], [23333.0, 2.0], [25765.0, 2.0], [14482.0, 1.0], [14930.0, 3.0], [15058.0, 1.0], [15538.0, 3.0], [15602.0, 2.0], [15634.0, 2.0], [15762.0, 3.0], [16114.0, 3.0], [15922.0, 3.0], [16178.0, 1.0], [16740.0, 2.0], [16996.0, 2.0], [16420.0, 2.0], [16612.0, 2.0], [17124.0, 2.0], [16932.0, 2.0], [16804.0, 2.0], [17636.0, 2.0], [17828.0, 2.0], [18212.0, 2.0], [17764.0, 2.0], [19236.0, 2.0], [18980.0, 2.0], [19940.0, 2.0], [19748.0, 2.0], [19812.0, 2.0], [19684.0, 2.0], [19556.0, 2.0], [20388.0, 2.0], [20964.0, 2.0], [20836.0, 2.0], [20900.0, 2.0], [21348.0, 2.0], [21092.0, 2.0], [21156.0, 2.0], [21668.0, 2.0], [21796.0, 2.0], [22564.0, 2.0], [25700.0, 2.0], [29668.0, 1.0], [16491.0, 2.0], [17259.0, 2.0], [16811.0, 2.0], [16619.0, 2.0], [17067.0, 2.0], [17003.0, 1.0], [18347.0, 2.0], [17835.0, 2.0], [17707.0, 3.0], [17515.0, 2.0], [18603.0, 2.0], [18539.0, 2.0], [18731.0, 2.0], [18667.0, 2.0], [19435.0, 2.0], [19115.0, 1.0], [19499.0, 2.0], [20011.0, 2.0], [20395.0, 2.0], [20075.0, 2.0], [19947.0, 2.0], [20139.0, 2.0], [19563.0, 2.0], [20587.0, 2.0], [20843.0, 2.0], [21227.0, 2.0], [21163.0, 2.0], [21867.0, 2.0], [22635.0, 2.0], [25515.0, 2.0], [26155.0, 2.0], [26795.0, 2.0], [27243.0, 2.0], [14293.0, 2.0], [15061.0, 2.0], [15669.0, 2.0], [15797.0, 2.0], [15509.0, 2.0], [15637.0, 2.0], [15413.0, 3.0], [15541.0, 3.0], [16309.0, 2.0], [16245.0, 2.0], [16053.0, 1.0], [17194.0, 2.0], [17386.0, 2.0], [16746.0, 2.0], [16490.0, 3.0], [17130.0, 2.0], [17898.0, 2.0], [18026.0, 2.0], [18538.0, 2.0], [19114.0, 2.0], [19306.0, 2.0], [18474.0, 2.0], [18858.0, 2.0], [18794.0, 2.0], [18986.0, 2.0], [19626.0, 2.0], [20010.0, 2.0], [19946.0, 2.0], [20458.0, 2.0], [20330.0, 2.0], [19818.0, 2.0], [19882.0, 2.0], [20138.0, 2.0], [20778.0, 2.0], [21290.0, 2.0], [20714.0, 2.0], [20522.0, 2.0], [20906.0, 2.0], [22058.0, 2.0], [22122.0, 2.0], [23402.0, 2.0], [24554.0, 2.0], [23978.0, 2.0], [17257.0, 2.0], [16937.0, 1.0], [18409.0, 2.0], [19241.0, 2.0], [19113.0, 2.0], [18793.0, 2.0], [19177.0, 2.0], [18537.0, 2.0], [19049.0, 2.0], [19753.0, 2.0], [20137.0, 2.0], [20201.0, 2.0], [20265.0, 2.0], [20457.0, 2.0], [20393.0, 2.0], [19881.0, 2.0], [20585.0, 2.0], [21161.0, 2.0], [20713.0, 2.0], [22505.0, 2.0], [22185.0, 2.0], [22377.0, 2.0], [23209.0, 2.0], [22569.0, 2.0], [24233.0, 2.0], [24489.0, 2.0], [24617.0, 2.0], [25065.0, 2.0], [25257.0, 2.0], [25001.0, 2.0], [24681.0, 2.0], [27369.0, 2.0], [27817.0, 1.0], [32489.0, 1.0], [2861.0, 0.0], [13268.0, 3.0], [13652.0, 2.0], [13844.0, 2.0], [14804.0, 3.0], [14612.0, 2.0], [14452.0, 3.0], [14836.0, 2.0], [15252.0, 2.0], [15348.0, 2.0], [16180.0, 2.0], [16084.0, 3.0], [16020.0, 2.0], [17192.0, 2.0], [16744.0, 2.0], [16424.0, 2.0], [18088.0, 2.0], [18408.0, 2.0], [17896.0, 2.0], [18344.0, 3.0], [17640.0, 2.0], [19240.0, 2.0], [18664.0, 2.0], [19560.0, 2.0], [20136.0, 2.0], [19816.0, 2.0], [19624.0, 2.0], [19688.0, 2.0], [19496.0, 2.0], [20712.0, 2.0], [21480.0, 2.0], [20840.0, 2.0], [21608.0, 2.0], [21736.0, 2.0], [22120.0, 2.0], [24296.0, 2.0], [24040.0, 2.0], [24680.0, 2.0], [25384.0, 2.0], [26088.0, 2.0], [17135.0, 2.0], [16687.0, 2.0], [17391.0, 2.0], [16431.0, 3.0], [17519.0, 3.0], [18031.0, 2.0], [17711.0, 2.0], [18735.0, 2.0], [18927.0, 2.0], [18479.0, 2.0], [19311.0, 2.0], [19375.0, 2.0], [19183.0, 2.0], [19695.0, 2.0], [20143.0, 2.0], [20079.0, 2.0], [19503.0, 2.0], [19951.0, 2.0], [19759.0, 2.0], [19823.0, 2.0], [20399.0, 2.0], [19631.0, 2.0], [20975.0, 2.0], [21039.0, 2.0], [21167.0, 2.0], [21295.0, 2.0], [20847.0, 2.0], [22127.0, 2.0], [22319.0, 2.0], [22767.0, 2.0], [24175.0, 2.0], [25007.0, 2.0], [25839.0, 2.0], [31087.0, 1.0], [13879.0, 2.0], [14743.0, 2.0], [14615.0, 2.0], [14839.0, 2.0], [15287.0, 2.0], [15863.0, 2.0], [16055.0, 3.0], [16087.0, 1.0], [16942.0, 2.0], [16622.0, 2.0], [17070.0, 2.0], [17006.0, 1.0], [16814.0, 2.0], [16494.0, 2.0], [16878.0, 2.0], [16558.0, 1.0], [17198.0, 1.0], [16750.0, 1.0], [17966.0, 2.0], [18414.0, 2.0], [17838.0, 2.0], [17582.0, 3.0], [18542.0, 2.0], [19054.0, 2.0], [19118.0, 2.0], [18862.0, 2.0], [18798.0, 2.0], [18926.0, 2.0], [18670.0, 2.0], [19374.0, 2.0], [19438.0, 2.0], [19630.0, 2.0], [20334.0, 2.0], [20142.0, 2.0], [19822.0, 2.0], [19886.0, 2.0], [19566.0, 2.0], [21358.0, 2.0], [21038.0, 2.0], [20526.0, 2.0], [20718.0, 2.0], [20846.0, 2.0], [23662.0, 2.0], [25518.0, 2.0], [24942.0, 2.0], [27182.0, 2.0], [17197.0, 3.0], [17325.0, 3.0], [17005.0, 2.0], [16621.0, 1.0], [16749.0, 1.0], [17837.0, 2.0], [17581.0, 2.0], [17453.0, 2.0], [17709.0, 2.0], [18349.0, 2.0], [17901.0, 2.0], [18477.0, 2.0], [18797.0, 2.0], [18541.0, 2.0], [19309.0, 2.0], [19245.0, 2.0], [18989.0, 2.0], [19053.0, 2.0], [20077.0, 2.0], [19757.0, 2.0], [19565.0, 2.0], [19501.0, 2.0], [20141.0, 2.0], [19949.0, 1.0], [20461.0, 2.0], [19693.0, 2.0], [20525.0, 2.0], [21037.0, 2.0], [20845.0, 2.0], [22893.0, 2.0], [23021.0, 2.0], [13302.0, 2.0], [13782.0, 3.0], [14358.0, 3.0], [14518.0, 3.0], [15318.0, 2.0], [15094.0, 2.0], [15382.0, 1.0], [15478.0, 2.0], [15702.0, 2.0], [15734.0, 2.0], [15574.0, 2.0], [15606.0, 2.0], [15766.0, 2.0], [16054.0, 2.0], [16876.0, 2.0], [16556.0, 2.0], [16684.0, 2.0], [17004.0, 2.0], [16492.0, 2.0], [19244.0, 2.0], [19308.0, 2.0], [19116.0, 2.0], [18732.0, 3.0], [19820.0, 2.0], [19628.0, 2.0], [19948.0, 2.0], [20268.0, 2.0], [19500.0, 2.0], [20076.0, 2.0], [19692.0, 2.0], [19756.0, 2.0], [20716.0, 2.0], [22380.0, 2.0], [22444.0, 2.0], [22956.0, 2.0], [22572.0, 2.0], [24044.0, 2.0], [26092.0, 2.0], [26860.0, 2.0], [17075.0, 2.0], [17011.0, 2.0], [18227.0, 2.0], [18035.0, 2.0], [17779.0, 2.0], [17971.0, 2.0], [18291.0, 2.0], [18099.0, 2.0], [18419.0, 3.0], [17843.0, 1.0], [18611.0, 3.0], [18995.0, 2.0], [18867.0, 2.0], [19123.0, 2.0], [18547.0, 2.0], [19571.0, 2.0], [19827.0, 2.0], [20211.0, 2.0], [19507.0, 2.0], [19763.0, 2.0], [20659.0, 2.0], [20787.0, 2.0], [21427.0, 2.0], [20723.0, 2.0], [21171.0, 2.0], [22003.0, 2.0], [23155.0, 2.0], [25139.0, 2.0], [25331.0, 2.0], [27827.0, 2.0], [13017.0, 2.0], [13849.0, 2.0], [14841.0, 2.0], [14713.0, 1.0], [15385.0, 2.0], [15897.0, 3.0], [16377.0, 2.0], [17202.0, 2.0], [16882.0, 1.0], [18418.0, 2.0], [17842.0, 2.0], [18098.0, 2.0], [17458.0, 2.0], [18162.0, 3.0], [19058.0, 2.0], [19442.0, 2.0], [18674.0, 2.0], [19570.0, 2.0], [19826.0, 2.0], [19506.0, 2.0], [19954.0, 2.0], [20466.0, 2.0], [20402.0, 2.0], [20658.0, 2.0], [21298.0, 2.0], [21170.0, 2.0], [20722.0, 2.0], [22386.0, 2.0], [22450.0, 2.0], [23154.0, 2.0], [17393.0, 2.0], [16497.0, 1.0], [16817.0, 3.0], [17585.0, 1.0], [18097.0, 2.0], [18417.0, 2.0], [18289.0, 2.0], [18545.0, 2.0], [19121.0, 2.0], [18993.0, 2.0], [19057.0, 2.0], [18609.0, 2.0], [18737.0, 2.0], [19185.0, 2.0], [19569.0, 2.0], [19697.0, 2.0], [19761.0, 2.0], [19953.0, 2.0], [20401.0, 2.0], [20337.0, 2.0], [20145.0, 2.0], [20273.0, 2.0], [21169.0, 2.0], [20657.0, 2.0], [21105.0, 2.0], [22065.0, 2.0], [23217.0, 2.0], [22769.0, 2.0], [23729.0, 2.0], [24177.0, 2.0], [25137.0, 2.0], [29681.0, 1.0], [4860.0, 1.0], [13304.0, 1.0], [14168.0, 2.0], [13976.0, 1.0], [13848.0, 1.0], [15320.0, 3.0], [15192.0, 2.0], [15256.0, 3.0], [15384.0, 2.0], [15544.0, 3.0], [15864.0, 3.0], [16056.0, 3.0], [16752.0, 2.0], [16880.0, 2.0], [16432.0, 2.0], [17392.0, 2.0], [16816.0, 2.0], [17328.0, 3.0], [17264.0, 1.0], [16688.0, 1.0], [17520.0, 2.0], [18224.0, 2.0], [18096.0, 2.0], [17456.0, 2.0], [17840.0, 2.0], [18608.0, 2.0], [18736.0, 2.0], [19120.0, 2.0], [18928.0, 2.0], [18672.0, 2.0], [19184.0, 1.0], [20272.0, 2.0], [19888.0, 2.0], [20336.0, 2.0], [19696.0, 2.0], [19632.0, 2.0], [19952.0, 2.0], [19568.0, 2.0], [19824.0, 2.0], [19760.0, 2.0], [20080.0, 2.0], [20016.0, 2.0], [20400.0, 2.0], [23472.0, 2.0], [24560.0, 2.0], [17271.0, 2.0], [16567.0, 2.0], [16823.0, 2.0], [16887.0, 2.0], [17399.0, 2.0], [16439.0, 3.0], [17015.0, 1.0], [17207.0, 2.0], [17463.0, 2.0], [18103.0, 2.0], [17719.0, 2.0], [19447.0, 2.0], [18551.0, 2.0], [19191.0, 2.0], [19319.0, 3.0], [18615.0, 2.0], [18935.0, 2.0], [19511.0, 2.0], [19831.0, 2.0], [19895.0, 2.0], [19767.0, 1.0], [20343.0, 2.0], [19703.0, 2.0], [20087.0, 2.0], [20663.0, 2.0], [21111.0, 2.0], [20599.0, 2.0], [22263.0, 2.0], [22839.0, 2.0], [23223.0, 2.0], [25335.0, 2.0], [28727.0, 2.0], [9819.0, 1.0], [14811.0, 2.0], [15835.0, 2.0], [16347.0, 2.0], [16187.0, 3.0], [17398.0, 2.0], [17206.0, 2.0], [17142.0, 2.0], [16758.0, 2.0], [18230.0, 2.0], [19318.0, 2.0], [18486.0, 2.0], [18870.0, 2.0], [18934.0, 3.0], [19190.0, 2.0], [18998.0, 2.0], [18806.0, 2.0], [18550.0, 2.0], [19254.0, 2.0], [19382.0, 2.0], [19446.0, 2.0], [20022.0, 2.0], [19766.0, 2.0], [20214.0, 1.0], [20598.0, 2.0], [21046.0, 2.0], [20534.0, 2.0], [22646.0, 2.0], [25270.0, 2.0], [26102.0, 2.0], [25846.0, 2.0], [26678.0, 2.0], [16629.0, 2.0], [17141.0, 2.0], [17653.0, 2.0], [17845.0, 2.0], [17973.0, 2.0], [18037.0, 2.0], [18165.0, 2.0], [19445.0, 2.0], [18805.0, 2.0], [18485.0, 2.0], [18741.0, 2.0], [19189.0, 2.0], [19317.0, 2.0], [18869.0, 1.0], [19701.0, 2.0], [20405.0, 2.0], [19637.0, 2.0], [20149.0, 2.0], [19957.0, 2.0], [20277.0, 2.0], [20021.0, 2.0], [19573.0, 2.0], [20085.0, 2.0], [20789.0, 2.0], [20725.0, 2.0], [21301.0, 2.0], [21045.0, 2.0], [21749.0, 2.0], [22517.0, 2.0], [22325.0, 2.0], [21877.0, 2.0], [23989.0, 2.0], [25141.0, 2.0], [28405.0, 2.0], [28213.0, 2.0], [12666.0, 2.0], [14426.0, 1.0], [14362.0, 1.0], [15162.0, 3.0], [14938.0, 2.0], [15290.0, 2.0], [15674.0, 2.0], [15418.0, 3.0], [15386.0, 2.0], [15962.0, 1.0], [17140.0, 2.0], [16820.0, 2.0], [16500.0, 2.0], [17908.0, 3.0], [17780.0, 2.0], [17716.0, 2.0], [17844.0, 3.0], [18036.0, 2.0], [18420.0, 2.0], [17524.0, 2.0], [17652.0, 2.0], [19316.0, 2.0], [18676.0, 2.0], [18548.0, 2.0], [19380.0, 2.0], [18484.0, 2.0], [18740.0, 2.0], [19124.0, 2.0], [19188.0, 3.0], [19060.0, 2.0], [18996.0, 2.0], [18868.0, 2.0], [20404.0, 2.0], [20020.0, 2.0], [20468.0, 2.0], [20212.0, 2.0], [20276.0, 2.0], [19828.0, 2.0], [20084.0, 2.0], [20148.0, 2.0], [20660.0, 2.0], [21428.0, 2.0], [21748.0, 2.0], [23284.0, 2.0], [22708.0, 2.0], [24500.0, 2.0], [16891.0, 2.0], [16699.0, 3.0], [16635.0, 3.0], [17723.0, 2.0], [19387.0, 2.0], [18747.0, 2.0], [19323.0, 2.0], [18619.0, 3.0], [19451.0, 2.0], [19515.0, 2.0], [19771.0, 2.0], [20347.0, 2.0], [19707.0, 2.0], [20027.0, 2.0], [19643.0, 2.0], [20219.0, 2.0], [21179.0, 2.0], [20923.0, 2.0], [21499.0, 2.0], [20539.0, 2.0], [24891.0, 2.0], [26107.0, 2.0], [27515.0, 2.0], [13725.0, 2.0], [15101.0, 2.0], [15293.0, 1.0], [15389.0, 3.0], [16029.0, 2.0], [16381.0, 2.0], [16061.0, 3.0], [16349.0, 1.0], [16890.0, 2.0], [17018.0, 2.0], [17082.0, 2.0], [17402.0, 2.0], [18170.0, 2.0], [17786.0, 2.0], [17978.0, 2.0], [18298.0, 1.0], [18938.0, 3.0], [18490.0, 2.0], [19002.0, 2.0], [19066.0, 2.0], [19514.0, 2.0], [19706.0, 2.0], [19770.0, 2.0], [19898.0, 2.0], [19578.0, 2.0], [20410.0, 2.0], [19642.0, 2.0], [21114.0, 2.0], [21434.0, 2.0], [21370.0, 2.0], [21818.0, 2.0], [22650.0, 2.0], [25594.0, 2.0], [16505.0, 2.0], [16569.0, 3.0], [16953.0, 2.0], [17337.0, 3.0], [16889.0, 1.0], [16825.0, 3.0], [17145.0, 1.0], [18361.0, 2.0], [18297.0, 2.0], [17977.0, 2.0], [18233.0, 2.0], [18681.0, 2.0], [19449.0, 2.0], [19129.0, 2.0], [18809.0, 2.0], [19321.0, 2.0], [19193.0, 2.0], [20025.0, 2.0], [19833.0, 2.0], [19577.0, 2.0], [19897.0, 2.0], [19705.0, 2.0], [20665.0, 2.0], [20921.0, 2.0], [21817.0, 2.0], [22585.0, 2.0], [23545.0, 2.0], [24313.0, 2.0], [25337.0, 2.0], [27513.0, 2.0], [7054.0, 1.0], [7614.0, 1.0], [13756.0, 2.0], [15388.0, 2.0], [15772.0, 3.0], [15420.0, 1.0], [15804.0, 2.0], [15548.0, 1.0], [16252.0, 2.0], [16316.0, 2.0], [15900.0, 3.0], [16124.0, 3.0], [16060.0, 3.0], [17016.0, 2.0], [18040.0, 2.0], [17592.0, 2.0], [17848.0, 2.0], [17912.0, 2.0], [17784.0, 2.0], [18296.0, 2.0], [18616.0, 2.0], [18936.0, 2.0], [19448.0, 2.0], [18680.0, 2.0], [19384.0, 1.0], [19960.0, 2.0], [19896.0, 2.0], [20408.0, 2.0], [20152.0, 2.0], [19768.0, 2.0], [20024.0, 2.0], [19512.0, 2.0], [19832.0, 2.0], [19576.0, 2.0], [20536.0, 2.0], [21112.0, 2.0], [20856.0, 2.0], [21048.0, 2.0], [20792.0, 2.0], [22456.0, 1.0], [23352.0, 2.0], [25528.0, 2.0], [26552.0, 1.0], [31160.0, 1.0], [17151.0, 2.0], [17023.0, 2.0], [17215.0, 2.0], [16959.0, 1.0], [17599.0, 2.0], [18239.0, 2.0], [18431.0, 2.0], [17855.0, 2.0], [18175.0, 2.0], [17983.0, 2.0], [17919.0, 2.0], [18815.0, 2.0], [18623.0, 2.0], [18943.0, 2.0], [18559.0, 2.0], [19135.0, 1.0], [20351.0, 2.0], [19903.0, 2.0], [19583.0, 2.0], [20095.0, 2.0], [20031.0, 2.0], [20223.0, 2.0], [19519.0, 2.0], [22335.0, 2.0], [23871.0, 2.0], [15871.0, 2.0], [15679.0, 2.0], [17342.0, 2.0], [16638.0, 3.0], [17214.0, 3.0], [17278.0, 3.0], [17726.0, 2.0], [17662.0, 2.0], [18110.0, 2.0], [18430.0, 1.0], [18750.0, 2.0], [18878.0, 2.0], [19006.0, 2.0], [18494.0, 3.0], [18814.0, 2.0], [18686.0, 2.0], [18942.0, 1.0], [20350.0, 2.0], [19646.0, 2.0], [19582.0, 2.0], [19774.0, 2.0], [19966.0, 2.0], [20222.0, 2.0], [20158.0, 2.0], [20030.0, 2.0], [20286.0, 2.0], [20670.0, 2.0], [21118.0, 2.0], [21310.0, 2.0], [20798.0, 2.0], [21886.0, 2.0], [21758.0, 2.0], [23166.0, 2.0], [25726.0, 2.0], [30846.0, 1.0], [17149.0, 3.0], [17085.0, 1.0], [16445.0, 1.0], [16637.0, 1.0], [18237.0, 2.0], [18301.0, 2.0], [17661.0, 2.0], [19005.0, 2.0], [18621.0, 2.0], [19325.0, 2.0], [19069.0, 2.0], [18941.0, 2.0], [19389.0, 2.0], [19133.0, 2.0], [19453.0, 2.0], [19965.0, 2.0], [19837.0, 2.0], [20413.0, 2.0], [19581.0, 2.0], [20221.0, 2.0], [20349.0, 2.0], [21309.0, 2.0], [21181.0, 2.0], [21949.0, 2.0], [22077.0, 2.0], [23613.0, 2.0], [25213.0, 2.0], [26429.0, 2.0], [6191.0, 0.0], [11390.0, 2.0], [12062.0, 1.0], [14654.0, 2.0], [15230.0, 1.0], [15614.0, 2.0], [15582.0, 2.0], [15710.0, 2.0], [15806.0, 2.0], [16126.0, 2.0], [16382.0, 2.0], [16158.0, 1.0], [16956.0, 2.0], [16828.0, 2.0], [17148.0, 2.0], [17212.0, 1.0], [18108.0, 2.0], [17532.0, 2.0], [18300.0, 3.0], [17852.0, 1.0], [19324.0, 2.0], [19004.0, 2.0], [19196.0, 2.0], [19260.0, 2.0], [19132.0, 2.0], [18940.0, 2.0], [18748.0, 2.0], [18812.0, 3.0], [19708.0, 2.0], [20220.0, 2.0], [19580.0, 2.0], [21116.0, 2.0], [20604.0, 2.0], [20668.0, 2.0], [20540.0, 2.0], [21180.0, 2.0], [21564.0, 2.0], [23868.0, 2.0], [24060.0, 2.0], [26044.0, 2.0], [27196.0, 1.0], [27836.0, 2.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 32489.0, "title": "Latencies Vs Request"}},
    getOptions: function() {
        return{
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Latency in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: { noColumns: 2,show: true, container: '#legendLatencyVsRequest' },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median Latency time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesLatencyVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotLatenciesVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewLatenciesVsRequest"), dataset, prepareOverviewOptions(options));
    }
};

// Latencies vs Request
function refreshLatenciesVsRequest() {
        var infos = latenciesVsRequestInfos;
        prepareSeries(infos.data);
        if(isGraph($("#flotLatenciesVsRequest"))){
            infos.createGraph();
        }else{
            var choiceContainer = $("#choicesLatencyVsRequest");
            createLegend(choiceContainer, infos);
            infos.createGraph();
            setGraphZoomable("#flotLatenciesVsRequest", "#overviewLatenciesVsRequest");
            $('#footerLatenciesVsRequest .legendColorBox > div').each(function(i){
                $(this).clone().prependTo(choiceContainer.find("li").eq(i));
            });
        }
};

var hitsPerSecondInfos = {
        data: {"result": {"minY": 2625.4333333333334, "minX": 1.6414626E12, "maxY": 26091.0, "series": [{"data": [[1.64146362E12, 18723.466666666667], [1.64146392E12, 19518.183333333334], [1.64146302E12, 17430.5], [1.64146524E12, 2625.4333333333334], [1.64146332E12, 18923.05], [1.64146494E12, 18852.633333333335], [1.6414629E12, 16151.65], [1.64146512E12, 16280.083333333334], [1.6414632E12, 17471.8], [1.64146482E12, 22691.866666666665], [1.64146452E12, 18526.766666666666], [1.6414626E12, 4604.866666666667], [1.64146422E12, 18925.533333333333], [1.6414644E12, 19217.3], [1.6414641E12, 19883.083333333332], [1.6414635E12, 18960.816666666666], [1.6414638E12, 19062.166666666668], [1.64146338E12, 19330.533333333333], [1.64146368E12, 20069.8], [1.641465E12, 21768.466666666667], [1.64146278E12, 17161.8], [1.64146308E12, 16545.216666666667], [1.6414647E12, 18849.683333333334], [1.64146266E12, 26091.0], [1.64146488E12, 19087.133333333335], [1.64146296E12, 17221.966666666667], [1.64146458E12, 18864.916666666668], [1.64146428E12, 19453.683333333334], [1.64146398E12, 21537.5], [1.64146386E12, 19055.3], [1.64146416E12, 21450.833333333332], [1.64146326E12, 18344.716666666667], [1.64146356E12, 18383.766666666666], [1.64146518E12, 16891.616666666665], [1.64146314E12, 17495.8], [1.64146344E12, 19310.216666666667], [1.64146506E12, 22506.5], [1.64146476E12, 19002.783333333333], [1.64146284E12, 17395.05], [1.64146446E12, 21863.55], [1.64146464E12, 21495.6], [1.64146272E12, 18824.55], [1.64146434E12, 19725.483333333334], [1.64146374E12, 18804.433333333334], [1.64146404E12, 18952.233333333334]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.64146524E12, "title": "Hits Per Second"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of hits / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendHitsPerSecond"
                },
                selection: {
                    mode : 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y.2 hits/sec"
                }
            };
        },
        createGraph: function createGraph() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesHitsPerSecond"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotHitsPerSecond"), dataset, options);
            // setup overview
            $.plot($("#overviewHitsPerSecond"), dataset, prepareOverviewOptions(options));
        }
};

// Hits per second
function refreshHitsPerSecond(fixTimestamps) {
    var infos = hitsPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if (isGraph($("#flotHitsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesHitsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotHitsPerSecond", "#overviewHitsPerSecond");
        $('#footerHitsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var codesPerSecondInfos = {
        data: {"result": {"minY": 2625.616666666667, "minX": 1.6414626E12, "maxY": 26090.916666666668, "series": [{"data": [[1.64146362E12, 18723.483333333334], [1.64146392E12, 19518.2], [1.64146302E12, 17430.583333333332], [1.64146524E12, 2625.616666666667], [1.64146332E12, 18923.083333333332], [1.64146494E12, 18852.6], [1.6414629E12, 16151.566666666668], [1.64146512E12, 16280.333333333334], [1.6414632E12, 17471.766666666666], [1.64146482E12, 22691.866666666665], [1.64146452E12, 18526.75], [1.6414626E12, 4604.266666666666], [1.64146422E12, 18925.583333333332], [1.6414644E12, 19217.266666666666], [1.6414641E12, 19883.066666666666], [1.6414635E12, 18960.833333333332], [1.6414638E12, 19062.2], [1.64146338E12, 19330.5], [1.64146368E12, 20069.816666666666], [1.641465E12, 21768.45], [1.64146278E12, 17161.816666666666], [1.64146308E12, 16545.116666666665], [1.6414647E12, 18849.7], [1.64146266E12, 26090.916666666668], [1.64146488E12, 19087.233333333334], [1.64146296E12, 17221.966666666667], [1.64146458E12, 18864.933333333334], [1.64146428E12, 19453.666666666668], [1.64146398E12, 21537.433333333334], [1.64146386E12, 19055.316666666666], [1.64146416E12, 21450.816666666666], [1.64146326E12, 18344.783333333333], [1.64146356E12, 18383.716666666667], [1.64146518E12, 16891.666666666668], [1.64146314E12, 17495.833333333332], [1.64146344E12, 19310.166666666668], [1.64146506E12, 22506.8], [1.64146476E12, 19002.716666666667], [1.64146284E12, 17395.15], [1.64146446E12, 21863.616666666665], [1.64146464E12, 21495.583333333332], [1.64146272E12, 18824.416666666668], [1.64146434E12, 19725.483333333334], [1.64146374E12, 18804.416666666668], [1.64146404E12, 18952.233333333334]], "isOverall": false, "label": "200", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.64146524E12, "title": "Codes Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendCodesPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "Number of Response Codes %s at %x was %y.2 responses / sec"
                }
            };
        },
    createGraph: function() {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesCodesPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotCodesPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewCodesPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Codes per second
function refreshCodesPerSecond(fixTimestamps) {
    var infos = codesPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotCodesPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesCodesPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotCodesPerSecond", "#overviewCodesPerSecond");
        $('#footerCodesPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var transactionsPerSecondInfos = {
        data: {"result": {"minY": 2625.616666666667, "minX": 1.6414626E12, "maxY": 26090.916666666668, "series": [{"data": [[1.64146362E12, 18723.483333333334], [1.64146392E12, 19518.2], [1.64146302E12, 17430.583333333332], [1.64146524E12, 2625.616666666667], [1.64146332E12, 18923.083333333332], [1.64146494E12, 18852.6], [1.6414629E12, 16151.566666666668], [1.64146512E12, 16280.333333333334], [1.6414632E12, 17471.766666666666], [1.64146482E12, 22691.866666666665], [1.64146452E12, 18526.75], [1.6414626E12, 4604.266666666666], [1.64146422E12, 18925.583333333332], [1.6414644E12, 19217.266666666666], [1.6414641E12, 19883.066666666666], [1.6414635E12, 18960.833333333332], [1.6414638E12, 19062.2], [1.64146338E12, 19330.5], [1.64146368E12, 20069.816666666666], [1.641465E12, 21768.45], [1.64146278E12, 17161.816666666666], [1.64146308E12, 16545.116666666665], [1.6414647E12, 18849.7], [1.64146266E12, 26090.916666666668], [1.64146488E12, 19087.233333333334], [1.64146296E12, 17221.966666666667], [1.64146458E12, 18864.933333333334], [1.64146428E12, 19453.666666666668], [1.64146398E12, 21537.433333333334], [1.64146386E12, 19055.316666666666], [1.64146416E12, 21450.816666666666], [1.64146326E12, 18344.783333333333], [1.64146356E12, 18383.716666666667], [1.64146518E12, 16891.666666666668], [1.64146314E12, 17495.833333333332], [1.64146344E12, 19310.166666666668], [1.64146506E12, 22506.8], [1.64146476E12, 19002.716666666667], [1.64146284E12, 17395.15], [1.64146446E12, 21863.616666666665], [1.64146464E12, 21495.583333333332], [1.64146272E12, 18824.416666666668], [1.64146434E12, 19725.483333333334], [1.64146374E12, 18804.416666666668], [1.64146404E12, 18952.233333333334]], "isOverall": false, "label": "HTTP请求-success", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.64146524E12, "title": "Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTransactionsPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                }
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTransactionsPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTransactionsPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewTransactionsPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Transactions per second
function refreshTransactionsPerSecond(fixTimestamps) {
    var infos = transactionsPerSecondInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTransactionsPerSecond");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotTransactionsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTransactionsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTransactionsPerSecond", "#overviewTransactionsPerSecond");
        $('#footerTransactionsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var totalTPSInfos = {
        data: {"result": {"minY": 2625.616666666667, "minX": 1.6414626E12, "maxY": 26090.916666666668, "series": [{"data": [[1.64146362E12, 18723.483333333334], [1.64146392E12, 19518.2], [1.64146302E12, 17430.583333333332], [1.64146524E12, 2625.616666666667], [1.64146332E12, 18923.083333333332], [1.64146494E12, 18852.6], [1.6414629E12, 16151.566666666668], [1.64146512E12, 16280.333333333334], [1.6414632E12, 17471.766666666666], [1.64146482E12, 22691.866666666665], [1.64146452E12, 18526.75], [1.6414626E12, 4604.266666666666], [1.64146422E12, 18925.583333333332], [1.6414644E12, 19217.266666666666], [1.6414641E12, 19883.066666666666], [1.6414635E12, 18960.833333333332], [1.6414638E12, 19062.2], [1.64146338E12, 19330.5], [1.64146368E12, 20069.816666666666], [1.641465E12, 21768.45], [1.64146278E12, 17161.816666666666], [1.64146308E12, 16545.116666666665], [1.6414647E12, 18849.7], [1.64146266E12, 26090.916666666668], [1.64146488E12, 19087.233333333334], [1.64146296E12, 17221.966666666667], [1.64146458E12, 18864.933333333334], [1.64146428E12, 19453.666666666668], [1.64146398E12, 21537.433333333334], [1.64146386E12, 19055.316666666666], [1.64146416E12, 21450.816666666666], [1.64146326E12, 18344.783333333333], [1.64146356E12, 18383.716666666667], [1.64146518E12, 16891.666666666668], [1.64146314E12, 17495.833333333332], [1.64146344E12, 19310.166666666668], [1.64146506E12, 22506.8], [1.64146476E12, 19002.716666666667], [1.64146284E12, 17395.15], [1.64146446E12, 21863.616666666665], [1.64146464E12, 21495.583333333332], [1.64146272E12, 18824.416666666668], [1.64146434E12, 19725.483333333334], [1.64146374E12, 18804.416666666668], [1.64146404E12, 18952.233333333334]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.64146524E12, "title": "Total Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTotalTPS"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                },
                colors: ["#9ACD32", "#FF6347"]
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTotalTPS"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTotalTPS"), dataset, options);
        // setup overview
        $.plot($("#overviewTotalTPS"), dataset, prepareOverviewOptions(options));
    }
};

// Total Transactions per second
function refreshTotalTPS(fixTimestamps) {
    var infos = totalTPSInfos;
    // We want to ignore seriesFilter
    prepareSeries(infos.data, false, true);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 28800000);
    }
    if(isGraph($("#flotTotalTPS"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTotalTPS");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTotalTPS", "#overviewTotalTPS");
        $('#footerTotalTPS .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

// Collapse the graph matching the specified DOM element depending the collapsed
// status
function collapse(elem, collapsed){
    if(collapsed){
        $(elem).parent().find(".fa-chevron-up").removeClass("fa-chevron-up").addClass("fa-chevron-down");
    } else {
        $(elem).parent().find(".fa-chevron-down").removeClass("fa-chevron-down").addClass("fa-chevron-up");
        if (elem.id == "bodyBytesThroughputOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshBytesThroughputOverTime(true);
            }
            document.location.href="#bytesThroughputOverTime";
        } else if (elem.id == "bodyLatenciesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesOverTime(true);
            }
            document.location.href="#latenciesOverTime";
        } else if (elem.id == "bodyCustomGraph") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCustomGraph(true);
            }
            document.location.href="#responseCustomGraph";
        } else if (elem.id == "bodyConnectTimeOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshConnectTimeOverTime(true);
            }
            document.location.href="#connectTimeOverTime";
        } else if (elem.id == "bodyResponseTimePercentilesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimePercentilesOverTime(true);
            }
            document.location.href="#responseTimePercentilesOverTime";
        } else if (elem.id == "bodyResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeDistribution();
            }
            document.location.href="#responseTimeDistribution" ;
        } else if (elem.id == "bodySyntheticResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshSyntheticResponseTimeDistribution();
            }
            document.location.href="#syntheticResponseTimeDistribution" ;
        } else if (elem.id == "bodyActiveThreadsOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshActiveThreadsOverTime(true);
            }
            document.location.href="#activeThreadsOverTime";
        } else if (elem.id == "bodyTimeVsThreads") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTimeVsThreads();
            }
            document.location.href="#timeVsThreads" ;
        } else if (elem.id == "bodyCodesPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCodesPerSecond(true);
            }
            document.location.href="#codesPerSecond";
        } else if (elem.id == "bodyTransactionsPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTransactionsPerSecond(true);
            }
            document.location.href="#transactionsPerSecond";
        } else if (elem.id == "bodyTotalTPS") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTotalTPS(true);
            }
            document.location.href="#totalTPS";
        } else if (elem.id == "bodyResponseTimeVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeVsRequest();
            }
            document.location.href="#responseTimeVsRequest";
        } else if (elem.id == "bodyLatenciesVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesVsRequest();
            }
            document.location.href="#latencyVsRequest";
        }
    }
}

/*
 * Activates or deactivates all series of the specified graph (represented by id parameter)
 * depending on checked argument.
 */
function toggleAll(id, checked){
    var placeholder = document.getElementById(id);

    var cases = $(placeholder).find(':checkbox');
    cases.prop('checked', checked);
    $(cases).parent().children().children().toggleClass("legend-disabled", !checked);

    var choiceContainer;
    if ( id == "choicesBytesThroughputOverTime"){
        choiceContainer = $("#choicesBytesThroughputOverTime");
        refreshBytesThroughputOverTime(false);
    } else if(id == "choicesResponseTimesOverTime"){
        choiceContainer = $("#choicesResponseTimesOverTime");
        refreshResponseTimeOverTime(false);
    }else if(id == "choicesResponseCustomGraph"){
        choiceContainer = $("#choicesResponseCustomGraph");
        refreshCustomGraph(false);
    } else if ( id == "choicesLatenciesOverTime"){
        choiceContainer = $("#choicesLatenciesOverTime");
        refreshLatenciesOverTime(false);
    } else if ( id == "choicesConnectTimeOverTime"){
        choiceContainer = $("#choicesConnectTimeOverTime");
        refreshConnectTimeOverTime(false);
    } else if ( id == "choicesResponseTimePercentilesOverTime"){
        choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        refreshResponseTimePercentilesOverTime(false);
    } else if ( id == "choicesResponseTimePercentiles"){
        choiceContainer = $("#choicesResponseTimePercentiles");
        refreshResponseTimePercentiles();
    } else if(id == "choicesActiveThreadsOverTime"){
        choiceContainer = $("#choicesActiveThreadsOverTime");
        refreshActiveThreadsOverTime(false);
    } else if ( id == "choicesTimeVsThreads"){
        choiceContainer = $("#choicesTimeVsThreads");
        refreshTimeVsThreads();
    } else if ( id == "choicesSyntheticResponseTimeDistribution"){
        choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        refreshSyntheticResponseTimeDistribution();
    } else if ( id == "choicesResponseTimeDistribution"){
        choiceContainer = $("#choicesResponseTimeDistribution");
        refreshResponseTimeDistribution();
    } else if ( id == "choicesHitsPerSecond"){
        choiceContainer = $("#choicesHitsPerSecond");
        refreshHitsPerSecond(false);
    } else if(id == "choicesCodesPerSecond"){
        choiceContainer = $("#choicesCodesPerSecond");
        refreshCodesPerSecond(false);
    } else if ( id == "choicesTransactionsPerSecond"){
        choiceContainer = $("#choicesTransactionsPerSecond");
        refreshTransactionsPerSecond(false);
    } else if ( id == "choicesTotalTPS"){
        choiceContainer = $("#choicesTotalTPS");
        refreshTotalTPS(false);
    } else if ( id == "choicesResponseTimeVsRequest"){
        choiceContainer = $("#choicesResponseTimeVsRequest");
        refreshResponseTimeVsRequest();
    } else if ( id == "choicesLatencyVsRequest"){
        choiceContainer = $("#choicesLatencyVsRequest");
        refreshLatenciesVsRequest();
    }
    var color = checked ? "black" : "#818181";
    if(choiceContainer != null) {
        choiceContainer.find("label").each(function(){
            this.style.color = color;
        });
    }
}

