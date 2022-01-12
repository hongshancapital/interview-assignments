//
//  WMAddButton.swift
//  WMSwiftUICode
//
//  Created by 王明民 on 2022/1/10.
//

import SwiftUI

struct WMAddButton: View {
    
    @State var theKey : String = WMModelData().todoKeys[0]
    @State var message: String  = ""
    @State var editing: Bool  = false
    
    var body: some View {
        
        HStack (alignment: .bottom) {
            
            editingButton(theKey: $theKey, editing: $editing)
            
            if editing {
                keyButton(theKey: $theKey, editing: $editing)
            }
        }
    }
}


struct editingButton : View {
    
    @State var message: String  = ""

    @Binding var theKey : String
    @Binding var editing: Bool
    
    @EnvironmentObject var todoLists : WMModelData
    @FocusState var isFocused : Bool

    var body: some View {
        Button(action: {
            print("button")
            isFocused = true
        }, label: {
            HStack {
                TextField(
                    "Add Todo ..." ,
                    text: $message,
                    onEditingChanged: { _ in
                        editing.toggle()
                    },
                    onCommit: {
                        if message.count > 0 {
                            todoLists.addTodo(todo: message, theKey)
                            message = ""
                        }
                    }
                )
                    .font(.system(size: 13))
                    .foregroundColor(.black)
                    .frame(height: 50, alignment: .leading)
                    .padding(.leading, 15)
                    .padding(.trailing, 5)
                    .multilineTextAlignment(.leading)
                    .background(.white)
                    .focused($isFocused)
                    

            }


        })
            .background(.white)
            .clipShape(RoundedRectangle(cornerRadius: 10.0))
            .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color(white: 0, opacity: 0.1), lineWidth: 1))
            .foregroundColor(.gray)
            .padding(.leading, 15)
            .padding(.trailing, editing ? 5 : 15)
            .padding(.bottom, 15)
    }
}


struct keyButton : View {
    
    @State var chooseKey : Bool = false
    
    @Binding var theKey : String
    @Binding var editing: Bool
    
    @EnvironmentObject var todoLists : WMModelData

    var body: some View {
        
        Button(action: {
            print("change group")
            withAnimation {
                chooseKey.toggle()
            }
        }, label: {
            VStack (spacing: 0) {
                
                if (chooseKey) {
                    
                    VStack (spacing: 0) {
                        
                        ForEach(todoLists.todoKeys, id: \.self) { oneKey in
                            Text(oneKey)
                                .frame(width: 90, height: 30, alignment: .center)
                                .clipShape(RoundedRectangle(cornerRadius: 10.0))
                                .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color(white: 0, opacity: 0.1), lineWidth: 1))
                                .padding(.top, 5)
                                .onTapGesture {
                                    withAnimation (.linear(duration: 0.1)) {
                                        theKey = oneKey
                                        chooseKey = false
                                    }
                                }
                        }
                        
                        
                        
                    }
                    .font(.system(size: 10))
                    .foregroundColor(.black)
                    .frame(
                        width:90,
                        height: 35.0 * CGFloat((todoLists.todoKeys.count)),
                        alignment: .leading
                    )
                    .padding(.leading, 5)
                    .padding(.trailing, 5)
                    .background(Color.white)
                }
                
                
                HStack {
                    Text(theKey)
                        .font(.system(size: 10))
                        .foregroundColor(.black)
                        .frame(width: 90, height: 50, alignment: .center)
                }
                
            }

            
        })
            .background(.white)
            .clipShape(RoundedRectangle(cornerRadius: 10.0))
            .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color(white: 0, opacity: 0.1), lineWidth: 1))
            .foregroundColor(.gray)
            .padding(.trailing, 15)
            .padding(.bottom, 15)
        
    }
    
}

