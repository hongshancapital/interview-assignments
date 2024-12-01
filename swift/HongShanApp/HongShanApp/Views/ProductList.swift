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
    
    init() {
        UINavigationBar.appearance().backgroundColor = UIColor.init(red: 190.0 / 255, green: 210.0 / 255, blue: 174.0 / 255, alpha: 1.0)
        UITableView.appearance().backgroundColor = .clear
        UITextField.appearance().backgroundColor = .clear
    }
    
    var body: some View {
 
        VStack(alignment: .leading){
            
            NavigationView{
                
                VStack{
                    LIST_BACK_COLOR
                        .edgesIgnoringSafeArea(.all)
                        .frame(width: SCREEN_WIDTH, height: 0.0, alignment: .top)

                    TextField(
                        userInputPlaceholder,
                        text: $userInputString,
                        onCommit:{
                            commitUserInput()
                        }
                    )
                    .onChange(of: userInputString, perform: { value in
                        commitUserInput()
                    })
                    .frame(width: SCREEN_WIDTH - 10.0 * 2, height: 50, alignment: .center)
                    .foregroundColor(.black)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .frame(width: SCREEN_WIDTH, height: 50, alignment: .center)
                    .background(LIST_BACK_COLOR)
                
                    if(!isShowList){
                        Text(noResultStr)
                            .frame(minWidth: 0, idealWidth: .infinity, maxWidth: .infinity, minHeight: 0, idealHeight: .infinity, maxHeight: .infinity, alignment: .center)
                            .background(LIST_BACK_COLOR)
                            .edgesIgnoringSafeArea(.all)
                    }else{
                    
                        List(selection: $singleSelection){
                            ForEach(pageHeaderModels){ headerModel in
                                Section(header: Text(headerModel.title)
                                            .font(.subheadline)
                                )
                                {
                                    ForEach(headerModel.products){productModel in
                                        ProductRow(productModel: productModel)
                                            .listRowInsets(.none)
                                            .listRowBackground(RGB(194, 215, 179))
                                    }
                                }
                            }
                        }
                        .background(LIST_BACK_COLOR)
                        .listStyle(GroupedListStyle())
                        .edgesIgnoringSafeArea(.all)
                    }
                }
                .navigationBarTitle(Text("Search"))
                .background(LIST_BACK_COLOR)
            }
        }
    }
    
    func commitUserInput() {
        print("commitUserInput text is: \(userInputString)")
        //模拟数据请求
        if userInputString.lowercased() == "dyson" {
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

