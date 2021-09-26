//
//  SwiftUIView.swift
//  
//
//  Created by Changgeng Wang on 2021/9/26.
//

import SwiftUI


/// 查询输入框视图
public struct SearchBar: View {
    @Binding var text:String
    @State var isEditing:Bool = false
    var closeAction: ((String?) -> Void) = {_ in }
    let height:CGFloat = 32
    
    public init(text:Binding<String>) {
        _text = text
    }
    public var body: some View {
        ZStack {
            Color(.sRGB, red: 0xD2 / 255, green: 0xD2 / 255, blue: 0xD2 / 255, opacity: 1)
            HStack {
                Image(systemName: "magnifyingglass")
                    .resizable().frame(width: 16, height: 16, alignment: .leading)
                    .foregroundColor(Color.gray)
                TextField("", text: $text) { value in
                    isEditing = value
                } onCommit: {
                }
                .font(.body)
            }
            #if os(iOS)
            .autocapitalization(.none)
            .disableAutocorrection(true)
            #endif
            .padding(.leading, 10)
            .padding(.trailing, 10)
            .padding(.top, 10)
            .padding(.bottom, 10)
            
            if isEditing {
                HStack {
                    Spacer()
                    Button {
                        text = ""
                        resignActive()
                        closeAction(text)
                    } label: {
                        HStack {
                            Image(systemName: "xmark").resizable()
                                .renderingMode(.template)
                                .foregroundColor(Color.gray)
                                .frame(width: 10, height: 10, alignment: .center)
                        }.frame(width: height, height: height, alignment: .center)
                    }.contentShape(Rectangle())
                }
            }
        }
        .frame(height: height)
        .cornerRadius(10)
        .padding(.leading)
        .padding(.trailing)
    }
    
    func resignActive(){
        
        #if os(iOS)
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
        #endif
    }
}

struct SearchBar_Previews: PreviewProvider {
    @State static var text:String = "AAA"
    static var previews: some View {
        SearchBar(text: $text)
    }
}
