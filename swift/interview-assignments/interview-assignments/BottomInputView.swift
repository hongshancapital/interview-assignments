//
//  BottomInputView.swift
//  interview-assignments
//
//  Created by never88gone on 2022/1/20.
//

import SwiftUI

struct BottomInputView: View {
    var groupNameList : [String]
    @Binding  var  groupName : String
    @State private var inputText = ""
    @State private var  showingActionSheet = false
    private  let   inputTipText = "Add new..."
    private  let   alertTitle = "Group Name"
    private  let   alertMessage = "Please Select Group Name"
    
    let appendTodoAction: ((String,String) -> Void)?
    
    var body: some View {
        var  buttons=[Alert.Button]()
        for oneTodoGroup in self.groupNameList {
            let button = ActionSheet.Button.default(Text(oneTodoGroup),action: {
                self.groupName=oneTodoGroup
            })
            buttons.append(button)
        }
        let cancelButton = ActionSheet.Button.default(Text("cancel"))
        buttons.append(cancelButton)
        
        return HStack {
            TextField(self.inputTipText, text:$inputText).frame(maxWidth: .infinity, idealHeight:  44).padding(EdgeInsets(top:0, leading:10, bottom: 0, trailing: 5)).background(Color.white).overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(
                        style: StrokeStyle(
                            lineWidth: 1,
                            dash: [5]
                        )
                    ).foregroundColor(self.groupNameList.count > 0 || self.groupName.count > 0  ? Color.clear : Color("ngtextgray"))
            ).cornerRadius(10).fixedSize(horizontal: false, vertical: true).foregroundColor(Color("ngtextback")).padding(EdgeInsets(top:5, leading:10, bottom: 5, trailing: 10)).onSubmit {
                
                self.appendTodoAction?(self.inputText,self.groupName)
            }
            if (self.groupNameList.count > 0 || self.groupName.count > 0){
                Button(action: {
                    self.showingActionSheet = true
                }){
                    HStack{
                        Text(self.groupName).fixedSize(horizontal: false, vertical: true).foregroundColor(Color("ngtextback")).font(.system(size: 12))
                        Image(systemName: "chevron.down").foregroundColor(Color("ngtextgray")).padding(.trailing,5).frame(width: 20, height: 20)
                    }.frame(maxHeight: .infinity).cornerRadius(10)
                }.frame(minWidth:50 ,maxWidth: 120, maxHeight: 32).background(Color.white).cornerRadius(10).padding(EdgeInsets(top:5, leading:0, bottom: 5, trailing: 5)).layoutPriority(1).actionSheet(isPresented: $showingActionSheet) {
                    ActionSheet(title: Text(self.alertTitle), message: Text(self.alertMessage), buttons: buttons
                    )
                }
            }
        }.background(Color.clear)
    }
}

struct BottomInputView_Previews: PreviewProvider {
    static var previews: some View {
        let groupNameList :[String] = ["aaa"]
        return BottomInputView(groupNameList: groupNameList,groupName : .constant("aaa"), appendTodoAction : {
            oneTitle, oneGroupName in
            
        }).previewLayout(.fixed(width: 375, height: 60))
    }
}
