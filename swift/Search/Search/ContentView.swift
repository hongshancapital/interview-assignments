//
//  ContentView.swift
//  Search
//
//  Created by chx on 2021/4/19.
//

import SwiftUI



struct ContentView: View {
    @State var categorys : [Categorys]
    @State private var searchName : String = "Dyson"
    var body: some View {
        NavigationView{
            GeometryReader{ geoReader in
                VStack(spacing:0){
                    SearchBar.init(searchName: $searchName)
                        .frame(width: UIScreen.main.bounds.width - 40, height: 50, alignment: .center)
                        .background(lightGrayColor)
                        .cornerRadius(10)
                        .offset(x:self.searchName == "Dyson" ? 0 : 20)
                    Group{
                        if self.searchName == "Dyson"{
                            List(categorys) { category in
                                CategoryView.init(category: category)
                                    .offset(x: -20)
                            }
                        }else if (self.searchName == ""){

                        }else{
                            Text("no result")
                                .frame(height:200)
                        }
                    }
                }
                .navigationBarTitle("Search",displayMode: .large)
                .navigationBarHidden(self.searchName != "")
            }

        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
    
}



struct ContentView_Previews: PreviewProvider {

    static var previews: some View {
        ContentView(categorys: Model.getSearchModel()!)
    }
}


