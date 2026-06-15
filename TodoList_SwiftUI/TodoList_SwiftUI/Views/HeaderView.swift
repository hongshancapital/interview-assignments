//
//  HeaderView.swift
//  TodoList_SwiftUI
//
//  Created by zengmuqiang on 2021/12/9.
//

import SwiftUI

struct HeaderView: View {
    
    @Environment(\.presentationMode) var presentationMode
    @EnvironmentObject var listViewModel: ListViewModel
    @State var contentStr: String = ""
    @State var animate:    Bool = false
    @State var showAlert:  Bool = false
    
    let secondaryAccentColor = Color("SecondaryAccentColor")
    
    var body: some View {
        VStack {
            Text("Header Type")
                .font(.title2)
                .bold()
                .padding(.horizontal)
            
            List {
                ForEach(listViewModel.sectionItems) { item in
                    HStack(alignment: .center, spacing: 10) {
                        Image(systemName: item.isChecked ? "checkmark.circle" : "circle")
                        Text(item.header)
                    }
                    .onTapGesture {
                        listViewModel.changeCheck(item: item)
                        presentationMode.wrappedValue.dismiss()
                    }
                }
                .onDelete(perform: listViewModel.deleteItem)
                .onMove(perform: listViewModel.moveItem)
                
            }
            .padding()
                .listStyle(PlainListStyle())
                .onAppear {
                    UITableView.appearance().separatorStyle = .none
                }
            
            Spacer()
            VStack {
                TextField("Please add header", text: self.$contentStr, onCommit: {
                    if contentStr.count < 1 {
                        self.showAlert = true
                    }
                    contentStr = ""
                    UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)
                })
                .frame(maxWidth: .infinity)
                .frame(height: 45)
                .padding(.horizontal, 15)
                .background(Color(UIColor.secondarySystemBackground))
                .cornerRadius(10)
                
                
                Button(action: {
                    if contentStr.count > 0 {
                        listViewModel.addItem(header: contentStr)
                    } else {
                        self.showAlert = true
                    }
                    self.contentStr = ""
                    UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)
                }) {
                    Text("Add Header")
                        .foregroundColor(.white)
                        .font(.subheadline)
                        .frame(maxWidth: .infinity)
                        .frame(height: 40)
                        .background(animate ? secondaryAccentColor : Color.accentColor)
                        .cornerRadius(8)
                        .padding(.horizontal, animate ? 15 : 25)
                        .shadow(color: animate ? secondaryAccentColor.opacity(0.7) : Color.accentColor.opacity(0.7), radius: animate ? 30 : 10, x: 0, y: animate ? 50 : 30)
                        .scaleEffect(animate ? 1.1 : 1.0)
                        .offset(y: animate ? -5 : 0)
                }
                .padding()
                .onAppear(perform: addAnimation)
            }
            .padding()
            .alert(isPresented: $showAlert, content: {
                Alert(title: Text("🤪"), message: Text("Please input header"), dismissButton: .default(Text("OK")))
            })
            
        }
        .navigationBarItems(trailing: EditButton())
    }
    
    func addAnimation() {
        guard !animate else { return }
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            withAnimation (
                Animation
                    .easeInOut(duration: 2.0)
                    .repeatForever()
            ) {
                animate.toggle()
            }
        }
    }
}

struct HeaderView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            HeaderView()
        }
        .environmentObject(ListViewModel())
    }
}
