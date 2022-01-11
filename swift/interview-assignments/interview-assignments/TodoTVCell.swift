//
//  ToDoTVCell.swift
//  interview-assignments
//
//  Created by never88gone on 2022/1/20.
//

import SwiftUI

/// todocell class
///
/// todocell
///
///
struct TodoTVCell: View {
    private let inputTipText : String = "Enter text, otherwise this row will be deleted"
    @FocusState private  var isNameFocused:Bool
    @State  var todo:Todo
    var cellTextChangedAction: ((String) -> Void)?
    var cellCheckedChangedAction: (() -> Void)?
    
    var body: some View {
        ZStack {
            HStack{
                Group {
                    if (self.todo.checked){
                        ZStack{
                            Image(systemName:"circle").resizable().frame(width: 30, height: 30).foregroundColor(Color("ngtextgray"))
                            Image(systemName: "circle.fill").resizable().frame(width: 15, height: 15).foregroundColor(Color("ngtextgray"))
                        }
                    }else {
                        Image(systemName: "circle").resizable().frame(width: 30, height: 30, alignment: .center).foregroundColor(Color("ngtextgray"))
                    }
                }.padding(.leading, 10.0).background(Color.clear).onTapGesture {
                    self.cellCheckedChangedAction?()
                }
                Group{
                    if (!self.todo.checked){
                        TextField(self.inputTipText, text: $todo.title)
                            .foregroundColor(Color("ngtextback"))
                            .textFieldStyle(PlainTextFieldStyle())
                            .focused($isNameFocused)
                            .fixedSize(horizontal: false, vertical: true)
                            .onChange(of: isNameFocused, perform: { newValue in
                                if (!newValue) {
                                    self.cellTextChangedAction?(self.todo.title)
                                }
                            })
                    }else {
                        Text(self.todo.title).strikethrough(true, color: Color("ngtextgray")).foregroundColor( Color("ngtextgray"))
                    }
                }.background(Color.clear).font(.custom("PingFangSC-Regular", size: 12).weight(.bold))
                Spacer()
            }
            if (self.todo.checked){
                ZStack{
                    Rectangle().frame(maxWidth: .infinity, maxHeight: 1, alignment:.center).padding().foregroundColor(Color("ngtextgraybackgroud"))
                    
                }.frame(maxWidth: .infinity,maxHeight: .infinity).background(Color.init(red: 0, green: 0, blue: 0, opacity: 0.1)).allowsHitTesting(false)
            }
        }.frame(minHeight:50,maxHeight: .infinity).background(Color.white).cornerRadius(10.0).padding(EdgeInsets(top: 5, leading: 5, bottom: 5, trailing:5))
            .onTapGesture{
                self.isNameFocused = false
            }.onLongPressGesture{
                self.isNameFocused = true
            }
    }
}


struct ToDoTVCell_Previews: PreviewProvider {
    static var previews: some View {
        let todo = Todo(title: "info", groupName: "haha")
        return TodoTVCell(todo:todo, cellTextChangedAction: {
            inputText in
            if (inputText.count == 0) {
                
            }
        }, cellCheckedChangedAction:{
            todo.checked.toggle()
        }).previewLayout(.fixed(width: 375, height: 100))
        
    }
}
