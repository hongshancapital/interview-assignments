//
//  InputTodoView.swift
//  ToDoList
//
//  Copyright © 2022 ZhangHeng. All rights reserved.
//

import SwiftUI

struct InputTodoView: View {
    @EnvironmentObject private var listViewModel: ListViewModel

    @State private var inputText: String = ""
    @State private var showingPopover = false
    @State private var showInput:Bool = false
    @State private var selectGroupIndex:Int = -1
    @State private var selectGroupTitle:String = "Select Group"
    @FocusState var addKeyboardShow: Bool

    
    var body: some View {
        HStack() {
            HStack() {
                TextField("Add New..." ,text: $inputText)
                    .font(.system(size: 13))
                    .frame(height: 55)
                    .textFieldStyle(PlainTextFieldStyle())
                    .padding([.horizontal], 4)
                    .cornerRadius(16)
                    .background(RoundedRectangle(cornerRadius: 16)
                                    .fill(Color.white))
                    .focused($addKeyboardShow)
                    .onSubmit {
                        addContent()
                    }
            }
            .padding(.init(top: 6, leading: 6, bottom: 6, trailing: 6))
        
            HStack(spacing: 0) {
                Button(action: {
                    showingPopover = true
                }, label: {
                    Text(selectGroupTitle)
                        .frame(minWidth: 40, idealWidth: 40, maxWidth: .infinity, minHeight: 30, idealHeight: 30, maxHeight: 60, alignment: .center)
                        .lineLimit(2)
                        .font(.bold(.system(size: 11))())
                        .foregroundColor(.black)
                })
                    .frame(width: 50, height:60, alignment: .leading)
                    .popover(isPresented: $showingPopover) {
                        List {
                            ForEach(self.listViewModel.listModel,id:\.self) { listGroup in
                                HStack {
                                    Text(listGroup.sectionTitle)
                                    Spacer()
                                }
                                .onTapGesture {
                                    self.selectGroupTitle = listGroup.sectionTitle
                                    showingPopover = false
                                }
                            }
                        }
                    }
                Image(systemName: "chevron.down")
                    .padding(3)
            }
            .background(.white)
            .cornerRadius(16)
            .padding(.init(top: 6, leading: 0, bottom: 6, trailing: 4))
        }
        .background(.clear)
        .onReceive(listViewModel.$isFocused) {
            addKeyboardShow = $0
        }
        .onAppear(){
            selectGroupTitle = listViewModel.listModel[0].sectionTitle
        }
    }
    
    func addContent() -> Void {
        showInput.toggle()
        listViewModel.showInputView = false
        if inputText.count > 0 {
            listViewModel.addTodo(content: inputText, sectionTitle: selectGroupTitle)
        }
        inputText = ""
    }
}

struct InputTodoView_Previews: PreviewProvider {
    static var previews: some View {
        let viewModel = loadViewModel()
        InputTodoView()
            .environmentObject(viewModel)
    }
    
    static func loadViewModel() -> ListViewModel {
        let listViewModel = ListViewModel();
        listViewModel.loadModel()
        return listViewModel
    }
}
