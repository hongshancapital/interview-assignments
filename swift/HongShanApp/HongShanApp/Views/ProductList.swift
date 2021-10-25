//
//  ProductList.swift
//  HongShanApp
//
//  Created by wangbin on 2021/10/22.
//

import SwiftUI

struct ProductList: View {
    @State private var singleSelection: UUID?
    @State private var userInputString: String = ""
    @State private var isEditing = false
    @State private var pageHeaderModels:[ProductHeaderModel] = []
    @State private var isShowList = false
    private let userInputPlaceholder = "Tap here to search"
    @State private var noResultStr = "No result"
    
    var body: some View {
//        Color.init(red: 190.0 / 255, green: 210.0 / 255, blue: 174.0 / 255).edgesIgnoringSafeArea(.all)
//        RGB(190, 210, 174).edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/)
        VStack{
            
            NavigationView{
                
                VStack{
                    
                    TextField(
                        userInputPlaceholder,
                        text: $userInputString,
                        onCommit:{
                            commitUserInput()
                        }
                    )
                    .frame(width: SCREEN_WIDTH - 10.0 * 2, height: 50, alignment: .top)
                    .foregroundColor(.black)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    
                    if(!isShowList){
                        Text(noResultStr)
                            .frame(minWidth: 0, idealWidth: .infinity, maxWidth: .infinity, minHeight: 0, idealHeight: .infinity, maxHeight: .infinity, alignment: .center)
                        Spacer()
                    }else{
                    
                        List(selection: $singleSelection){
                            ForEach(pageHeaderModels){ headerModel in
                                Section(header: Text(headerModel.title
                                )) {
                                    ForEach(headerModel.products){productModel in
                                        ProductRow(productModel: productModel)
                                    }
                                }
                            }
                    }
                        .listStyle(GroupedListStyle())
                        .frame(minWidth: 0, idealWidth: .infinity, maxWidth: .infinity, minHeight: 0, idealHeight: .infinity, maxHeight: .infinity, alignment: .center)
                    }
                }
                .navigationTitle("Search")
                    
            }
        }
    }
    
    func commitUserInput() {
        print("commitUserInput text is: \(userInputString)")
        if userInputString == "Dyson" {
            pageHeaderModels = headerModels
            isShowList = true
            noResultStr = ""
        }else{
            pageHeaderModels = []
            isShowList = false
            noResultStr = "No result"
        }
    }
}

struct ProductList_Previews: PreviewProvider {
    static var previews: some View {
        ProductList()
    }
}

