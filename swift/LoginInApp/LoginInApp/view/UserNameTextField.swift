//
//  UserNameTextField.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/9.
//

import Foundation
import SwiftUI

struct UserNameTextField: View {
    
    @Binding var userName: String 
    var placeText: String = "userName".localizedValue
    var changeTextValue: ((_ value: String) -> Void)?
    
    var body: some View {
     
        let binding = Binding<String>(get: {
            self.userName
        }, set: {
            self.userName = $0
            changeTextValue?($0)
            
        }) 
        return TextField(placeText, text: binding).font(Font.system(size: 16)).background(lineBackView(isSelected: false, color: Color(UIColor.secondarySystemBackground))).frame(height: IPHONEDeviceInfo.textFieldHeight, alignment: .center)
    }
    
}

