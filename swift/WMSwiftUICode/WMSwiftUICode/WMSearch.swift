//
//  WMSearch.swift
//  WMSwiftUICode
//
//  Created by wangmm on 2022/1/14.
//

import Foundation
import SwiftUI

struct WMSearch : View {
    
    @EnvironmentObject var todoLists : WMModelData
    @State var showSearch: Bool = false
    @State var word: String = ""
    @FocusState var isFocused : Bool
        
    var body: some View {
        
        Button(action: {
            print("search btn")
            
        }, label: {
            HStack {
                
                if showSearch {
                    TextField(
                        "search...",
                        text: $word,
                        onCommit: {
                            if word.count == 0 {
                                showSearch = false
                            } else {
                                showSearch = true
                            }
                            isFocused = false
                            todoLists.searchTodo(by: word)
                        }
                    )
                        .frame(width: 100, height: 30)
                        .padding(.leading, 5)
//                        .padding(.trailing, 5)
                        .multilineTextAlignment(.leading)
                        .font(.system(size: 13))
                        .clipShape(RoundedRectangle(cornerRadius: 10.0))
                        .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color(white: 0, opacity: 0.1), lineWidth: 1))
                        .focused($isFocused)
                    
                }
                
                Image.init(systemName: "magnifyingglass.circle.fill")
                    .padding(.trailing, 10)
                    .foregroundColor(.green)
                    .onTapGesture {
                        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 0.2) {
                            isFocused = true
                        }
                        withAnimation (.easeInOut(duration: 0.2)) {
                            if word.count == 0 {
                                showSearch.toggle()
                            } else {
                                showSearch = true
                            }

                        }
                    }
                
            }
            
        })
        
    }
}
