//
//  View+extenstion.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/5.
//

import SwiftUI

/// view extension for alter
extension View {

    public func defaultAlter<M>(
        _ titleKey: LocalizedStringKey,
        isPresented: Binding<Bool>,
        @ViewBuilder message: () -> M
    ) -> some View where M: View {

        self.alert(titleKey, isPresented: isPresented, actions: {
            Button("OK") { }
        }, message: message)
    }
}
