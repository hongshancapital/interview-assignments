//
//  TextFieldAlert.swift
//  interview-assignments
//
//  Created by never88gone on 2022/1/20.
//

import SwiftUI

struct TextFieldAlert<Presenting>: View where Presenting: View {
    @Binding var isShowing: Bool
    @State var showText: String = ""
    @Binding var text: String
    @FocusState private var isNameFocused: Bool
    
    let presenting: Presenting
    var placeholder: String = "Group Name"
    var title: String = "Add Group Name"
    
    @State private var showToast: Bool = false
    @State private var toastMessage: String = "Group Name can't be empty"
    
    var body: some View {
        GeometryReader { (deviceSize: GeometryProxy) in
            ZStack {
                self.presenting
                    .disabled(isShowing)
                ZStack {
                    VStack {}.frame(maxWidth: .infinity, maxHeight: .infinity).background(Color(red: 0, green: 0, blue: 0, opacity: 0.8))
                    
                    VStack {
                        HStack {
                            Spacer()
                            Button(action: {
                                self.showText = ""
                                withAnimation {
                                    self.isNameFocused = false
                                    self.isShowing.toggle()
                                }
                            }) {
                                Image(systemName: "xmark.circle").resizable().frame(width: 40, height: 40).foregroundColor(.white)
                            }.padding()
                        }
                        Spacer()
                    }.frame(maxWidth: .infinity, maxHeight: .infinity)
                    VStack {
                        Text(self.title).foregroundColor(Color("ngtextback"))
                        TextField(placeholder, text: $showText).focused($isNameFocused)
                        Divider()
                        HStack {
                            Button(action: {
                                if self.showText.count > 0 {
                                    self.text = self.showText
                                    self.isShowing.toggle()
                                    self.isNameFocused = false
                                    self.showText = ""
                                } else {
                                    withAnimation(Animation.easeInOut(duration: 0.5)) {
                                        self.showToast = true
                                    }
                                    DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                                        withAnimation(Animation.easeInOut(duration: 0.5)) {
                                            self.showToast = false
                                        }
                                    }
                                }
                            }) {
                                Text("OK").foregroundColor(Color("ngtextback")).frame(maxWidth: .infinity, maxHeight: .infinity)
                            }.frame(maxWidth: .infinity, maxHeight: .infinity)
                        }.frame(maxWidth: .infinity, maxHeight: 30)
                    }.padding()
                        .background(Color.white)
                        .frame(
                            width: deviceSize.size.width*0.7,
                            height: deviceSize.size.height*0.7
                        )
                        .cornerRadius(5)
                        .shadow(radius: 1)
                    
                    VStack {
                        Spacer()
                        Text(self.toastMessage).font(.custom("PingFangSC-Regular", size: 12)).padding().foregroundColor(.white)
                            .background(RoundedRectangle(cornerRadius: 15).fill(.black)).padding(EdgeInsets(top: 5, leading: 5, bottom: 100, trailing: 5))
                    }.opacity(self.showToast ? 1 : 0)
                }
                .opacity(self.isShowing ? 1 : 0)
            }
        }
    }
}

extension View {
    func textFieldAlert(isShowing: Binding<Bool>,
                        text: Binding<String>,
                        placeholder: String = "Group Name",
                        title: String = "Add Group Name") -> some View
    {
        TextFieldAlert(isShowing: isShowing,
                       text: text,
                       presenting: self,
                       placeholder: placeholder,
                       title: title)
    }
}

struct TextFieldAlert_Previews: PreviewProvider {
    static var previews: some View {
        return ZStack {}.previewLayout(.fixed(width: 375, height: 60)).textFieldAlert(isShowing: .constant(true), text: .constant("aaa"))
    }
}
