//
//  ShowAlertView.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/10.
//

import SwiftUI

struct ShowAlertView: View {
    @Binding var message: String
    var body: some View {
        Text(message).font(Font.system(size: 14)).frame(width: IPHONEDeviceInfo.screenWidth - 60, height: 40, alignment: .center).background(Color.rgbColor(255, g: 205, b: 2)).foregroundColor(Color.black).cornerRadius(20)
    }
}

struct ShowAlertView_Previews: PreviewProvider {
    static var previews: some View {
        ShowAlertView(message: .init(get: { () -> String in
            ""
        }, set: { (newvalue) in
            
        }))
    }
}
