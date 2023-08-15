//
//  EmptyDataView.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/14.
//

import SwiftUI

//You can continue to add other types of exceptions and related text
enum DataExceptionType{
    case empty
    case exception(errMsg: String)
}

struct EmptyDataView: View {
    let exceptionType: DataExceptionType
    var action: () ->Void
    
    
    var body: some View {
        
        VStack(alignment: .center){
            Text(exceptionTips())
                .multilineTextAlignment(.center)
                .foregroundColor(.red)
            Button {
                self.action()
            } label: {
                Text("RETRY")
            }
            .foregroundColor(.blue)
            .padding(.horizontal,18)
            .padding(.vertical, 6)
            .overlay(RoundedRectangle(cornerSize: CGSize(width: 4.0, height: 4.0),style: .circular).stroke(.blue, lineWidth: 1))
        }
    }
    
    private func exceptionTips() ->String{
        var ret = ""
        switch exceptionType{
            case .empty:
                ret = "no data, please click the button to try again"
                
            case .exception(let msg):
                ret = "load data exception: \(msg). please try again"
        }
        return ret
    }
}

#if DEBUG
struct EmptyDataView_Previews: PreviewProvider {
    static var previews: some View {
        EmptyDataView(exceptionType: .empty) {

        }
//        EmptyDataView(exceptionType: .ExceptionLoadingData(errMsg: "404 Error")) {
//
//        }
        
    }
}
#endif
