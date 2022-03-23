//
//  BBPageControl.swift
//  BBSwiftUIKit
//
//  Created by Kaibo Lu on 5/12/20.
//  Copyright © 2020 Kaibo Lu. All rights reserved.
//

import SwiftUI

public extension BBPageControl {
    func bb_hidesForSinglePage(_ hidesForSinglePage: Bool) -> BBPageControl {
        var view = self
        view.hidesForSinglePage = hidesForSinglePage
        return view
    }
    
    func bb_pageIndicatorTintColor(_ pageIndicatorTintColor: UIColor?) -> BBPageControl {
        var view = self
        view.pageIndicatorTintColor = pageIndicatorTintColor
        return view
    }
    
    func bb_currentPageIndicatorTintColor(_ currentPageIndicatorTintColor: UIColor?) -> BBPageControl {
        var view = self
        view.currentPageIndicatorTintColor = currentPageIndicatorTintColor
        return view
    }
}

public struct BBPageControl: UIViewRepresentable {
    @Binding var currentPage: Int
    var numberOfPages: Int
    var hidesForSinglePage: Bool = false
    var pageIndicatorTintColor: UIColor?
    var currentPageIndicatorTintColor: UIColor?
    
    public init(currentPage: Binding<Int>, numberOfPages: Int) {
        self._currentPage = currentPage
        self.numberOfPages = numberOfPages
    }
    
    public func makeUIView(context: Context) -> UIPageControl {
        let control = UIPageControl()
        control.addTarget(context.coordinator, action: #selector(Coordinator.updateCurrentPage(_:)), for: .valueChanged)
        return control
    }
    
    public func updateUIView(_ control: UIPageControl, context: Context) {
        control.numberOfPages = numberOfPages
        control.currentPage = currentPage // Set `currentPage` after `numberOfPages`
        control.hidesForSinglePage = hidesForSinglePage
        control.pageIndicatorTintColor = pageIndicatorTintColor
        control.currentPageIndicatorTintColor = currentPageIndicatorTintColor
    }
    
    public func makeCoordinator() -> Coordinator { Coordinator(self) }
    
    public class Coordinator: NSObject {
        let parent: BBPageControl
        
        init(_ view: BBPageControl) { parent = view }
        
        @objc func updateCurrentPage(_ sender: UIPageControl) {
            parent.currentPage = sender.currentPage
        }
    }
}
